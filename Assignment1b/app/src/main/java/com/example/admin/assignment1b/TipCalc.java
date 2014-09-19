package com.example.admin.assignment1b;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class TipCalc extends Activity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener,
        AdapterView.OnItemSelectedListener, Chronometer.OnChronometerTickListener {

    /* Initializes variables */
    TextView tvTip, tvTotal;
    EditText etBill;
    SeekBar tipSeekBar;
    Spinner problemSolvingSpinner;
    RadioButton rbGood, rbOK, rbBad;
    CheckBox cbFriendly, cbSpecials, cbOpinion;
    Chronometer waitTimer;
    boolean rbGoodChecked = false, rbOKChecked = false, rbBadChecked = false,
            spinnerOKSelected = true, spinnerGoodSelected = false, spinnerBadSelected = false,
            waitTimerRunning = false;
    int totalInitialValue = 0, seekBarInt;
    double bill, tip = 0.15, tipDisplay = 15.0, seekBarValue;
    long timeWhenStopped = 0;
    long tickCounter = -1;

    /* Sets up the TextWatcher */
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            CalculateTotal();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        /* Finds views from the ids defined in the XML file */
        tvTip = (TextView) findViewById(R.id.tvTip);
        tvTotal = (TextView) findViewById(R.id.tvTotal);
        etBill = (EditText) findViewById(R.id.etBillAmount);
        tipSeekBar = (SeekBar) findViewById(R.id.seekBarTipBar);
        problemSolvingSpinner = (Spinner) findViewById(R.id.spProbSolv);
        rbGood = (RadioButton) findViewById(R.id.radioButtonGood);
        rbOK = (RadioButton) findViewById(R.id.radioButtonOK);
        rbBad = (RadioButton) findViewById(R.id.radioButtonBad);
        cbFriendly = (CheckBox) findViewById(R.id.checkBoxFriendly);
        cbSpecials = (CheckBox) findViewById(R.id.checkBoxSpecials);
        cbOpinion = (CheckBox) findViewById(R.id.checkBoxOpinion);
        waitTimer = (Chronometer) findViewById(R.id.cmTimeElapsed);

        tvTip.setText(Double.toString(tipDisplay));
        tvTotal.setText(Integer.toString(totalInitialValue));

        /* Sets up the ArrayAdapter which will populate the problemSolvingSpinner with options */
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                (this, R.array.spinner_problemSolv, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        problemSolvingSpinner.setAdapter(adapter);

        /* Sets listeners */
        etBill.addTextChangedListener(textWatcher);
        tipSeekBar.setOnSeekBarChangeListener(this);
        rbGood.setOnClickListener(this);
        rbOK.setOnClickListener(this);
        rbBad.setOnClickListener(this);
        cbFriendly.setOnClickListener(this);
        cbSpecials.setOnClickListener(this);
        cbOpinion.setOnClickListener(this);
        problemSolvingSpinner.setOnItemSelectedListener(this);
        waitTimer.setOnChronometerTickListener(this);
    }

    /* A switch that checks what is clicked */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /* Code for CheckBoxes, different cases made for the 3 boxes.
            When a CheckBox is clicked it also updates the seekBar*/
            case R.id.checkBoxFriendly:
                seekBarValue = tipSeekBar.getProgress();
                if (cbFriendly.isChecked() && seekBarValue <= 49) {
                    seekBarValue = seekBarValue + 1;
                } else if (!cbFriendly.isChecked() && seekBarValue >= 1) {
                    seekBarValue = seekBarValue - 1;
                }
                UpdateDisplay();
                CalculateTotal();
                break;

            case R.id.checkBoxOpinion:
                seekBarValue = tipSeekBar.getProgress();
                if (cbOpinion.isChecked() && seekBarValue <= 49) {
                    seekBarValue = seekBarValue + 1;
                } else if (!cbOpinion.isChecked() && seekBarValue >= 1) {
                    seekBarValue = seekBarValue - 1;
                }
                UpdateDisplay();
                CalculateTotal();
                break;

            case R.id.checkBoxSpecials:
                seekBarValue = tipSeekBar.getProgress();
                if (cbSpecials.isChecked() && seekBarValue <= 49) {
                    seekBarValue = seekBarValue + 1;
                } else if (!cbSpecials.isChecked() && seekBarValue >= 1) {
                    seekBarValue = seekBarValue - 1;
                }
                UpdateDisplay();
                CalculateTotal();
                break;

            /* Code for RadioButton, different cases made for the 3 buttons.
            When a RadioButton is clicked it also updates the seekBar*/
            case R.id.radioButtonGood:
                rbGoodChecked = true;
                seekBarValue = tipSeekBar.getProgress();
                if (rbBadChecked && seekBarValue <= 48) {
                    seekBarValue = seekBarValue + 2;
                } else if (seekBarValue <= 49) {
                    seekBarValue = seekBarValue + 1;
                }
                rbOKChecked = false;
                rbBadChecked = false;
                UpdateDisplay();
                rbGood.setClickable(false);
                rbOK.setClickable(true);
                rbBad.setClickable(true);
                CalculateTotal();
                break;

            case R.id.radioButtonOK:
                rbOKChecked = true;
                seekBarValue = tipSeekBar.getProgress();
                if (rbGoodChecked && seekBarValue >= 1) {
                    seekBarValue = seekBarValue - 1;
                } else if (rbBadChecked && seekBarValue <= 49) {
                    seekBarValue = seekBarValue + 1;
                }
                rbBadChecked = false;
                rbGoodChecked = false;
                UpdateDisplay();
                rbOK.setClickable(false);
                rbGood.setClickable(true);
                rbBad.setClickable(true);
                CalculateTotal();
                break;

            case R.id.radioButtonBad:
                rbBadChecked = true;
                seekBarValue = tipSeekBar.getProgress();
                if (rbGoodChecked && seekBarValue >= 2) {
                    seekBarValue = seekBarValue - 2;
                } else if (seekBarValue >= 1) {
                    seekBarValue = seekBarValue - 1;
                }
                rbOKChecked = false;
                rbGoodChecked = false;
                UpdateDisplay();
                rbBad.setClickable(false);
                rbGood.setClickable(true);
                rbOK.setClickable(true);
                CalculateTotal();
                break;


        }

    }
    /*This method calculates the total bill and is called
    every time the user changes something.*/
    private void CalculateTotal() {
        String billAmount = etBill.getText().toString();
        if (!billAmount.isEmpty()) {
            bill = Double.parseDouble(billAmount);
            double newTotal = bill + (bill * tip);
            String formattedTotal = String.format("%.2f", newTotal);
            tvTotal.setText(formattedTotal);
        }
    }

    /* This method updates the tip and SeekBar display */
    private void UpdateDisplay() {
        tipDisplay = seekBarValue;
        seekBarInt = (int) seekBarValue;
        tipSeekBar.setProgress(seekBarInt);
        tip = seekBarValue / 100;
        tvTip.setText(Double.toString(tipDisplay));
    }


    /* This method updates the tip and total when the
    user interacts with the tip bar*/
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        seekBarValue = seekBar.getProgress();
        tipDisplay = seekBarValue;
        tip = seekBarValue / 100;
        tvTip.setText(Double.toString(tipDisplay));
        CalculateTotal();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    /* This method handles the user interaction with the Spinner */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int selectedItem = (problemSolvingSpinner.getSelectedItemPosition());
        switch (selectedItem) {
            case 0:
                spinnerOKSelected = true;
                seekBarValue = tipSeekBar.getProgress();
                if (spinnerGoodSelected && seekBarValue >= 1) {
                    seekBarValue = seekBarValue - 1;
                } else if (spinnerBadSelected && seekBarValue <= 49) {
                    seekBarValue = seekBarValue + 1;
                }
                spinnerGoodSelected = false;
                spinnerBadSelected = false;
                UpdateDisplay();
                CalculateTotal();
                break;

            case 1:
                spinnerGoodSelected = true;
                seekBarValue = tipSeekBar.getProgress();
                if (spinnerOKSelected && seekBarValue <= 49) {
                    seekBarValue = seekBarValue + 1;
                } else if (spinnerBadSelected && seekBarValue <= 48) {
                    seekBarValue = seekBarValue + 2;
                }
                spinnerOKSelected = false;
                spinnerBadSelected = false;
                UpdateDisplay();
                CalculateTotal();
                break;

            case 2:
                spinnerBadSelected = true;
                seekBarValue = tipSeekBar.getProgress();
                if (spinnerOKSelected && seekBarValue >= 1) {
                    seekBarValue = seekBarValue - 1;
                } else if (spinnerGoodSelected && seekBarValue >= 2) {
                    seekBarValue = seekBarValue - 2;
                }
                spinnerOKSelected = false;
                spinnerGoodSelected = false;
                UpdateDisplay();
                CalculateTotal();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    /* This method is for the Wait Timer. It is set to reduce the
    tip every 30 seconds you are waiting for your food while the timer is running.
    This timer resets when the user press the reset button*/
    @Override
    public void onChronometerTick(Chronometer chronometer) {
        seekBarValue = tipSeekBar.getProgress();
        if (tickCounter % 30 == 0 && tickCounter != 0 && seekBarValue >= 1) {
            seekBarValue = seekBarValue - 1;
            UpdateDisplay();
            ShowToast();
            CalculateTotal();
        }
        tickCounter++;
    }

    /* This method sets up and shows a toast when called */
    private void ShowToast(){
        Context context = getApplicationContext();
        String msg = getString(R.string.tipReducedMsg);
        CharSequence text = msg;
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    /*Code for the start button. It starts the timer when clicked.*/
    public void StartTime(View view) {
        waitTimer.setBase(SystemClock.elapsedRealtime() - timeWhenStopped);
        waitTimer.start();
        waitTimerRunning = true;
    }
    /*Code for the pause button. It pauses the timer when clicked.*/
    public void PauseTime(View view) {
        waitTimer.stop();
        timeWhenStopped = SystemClock.elapsedRealtime() - waitTimer.getBase();
        waitTimerRunning = false;
    }
    /*Code for the reset button. It resets the timer when clicked.*/
    public void ResetTime(View view) {
        waitTimer.setBase(SystemClock.elapsedRealtime());
        waitTimer.stop();
        timeWhenStopped = 0;
        tickCounter = -1;
        waitTimerRunning = false;
    }
}