package itp341.caceres.nicholas.tipCalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.EditText;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import itp341.caceres.nicholas.tipCalculator.R;

public class MainActivity extends AppCompatActivity {

    private TextView textViewPerPersonTipValue;
    private TextView textViewPerPersonTip;
    private TextView textViewPerPersonTotalValue;
    private TextView textViewPerPersonTotal;
    private TextView textViewPerPerson;
    private TextView textViewTotalValue;
    private TextView textViewTipValue;
    private TextView textViewPercentValue;

    private Spinner spinnerSplit;

    private SeekBar seekBarPercent;

    private EditText editTextBillAmount;

    private double billAmount;
    private double percent;
    private double tip;
    private double tipPerPerson;
    private double total;
    private double totalPerPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPercentValue = (TextView) findViewById(R.id.text_percent_value);
        textViewTipValue = (TextView) findViewById(R.id.text_tip_value);
        textViewTotalValue = (TextView) findViewById(R.id.text_total_value);
        textViewPerPersonTipValue = (TextView) findViewById(R.id.text_per_person_tip_value);
        textViewPerPersonTip = (TextView) findViewById(R.id.text_per_person_tip);
        textViewPerPersonTotalValue = (TextView) findViewById(R.id.text_per_person_total_value);
        textViewPerPersonTotal = (TextView) findViewById(R.id.text_per_person_total);
        textViewPerPerson = (TextView) findViewById(R.id.text_per_person);

        spinnerSplit = (Spinner) findViewById(R.id.spinnerSplit);

        seekBarPercent = (SeekBar) findViewById(R.id.seek_bar);
        percent = 15.00;

        editTextBillAmount = (EditText) findViewById(R.id.edit_bill_amount);

        //billAmountEditorListener billAmountListener = new billAmountEditorListener();
        //editTextBillAmount.setOnEditorActionListener(billAmountListener);
        editTextBillAmount.addTextChangedListener(new BillAmountTextWatcher());
        percentSeekBarListener percentSeekListener = new percentSeekBarListener();
        seekBarPercent.setOnSeekBarChangeListener(percentSeekListener);
        spinnerSplitListener splitListener = new spinnerSplitListener();
        spinnerSplit.setOnItemSelectedListener(splitListener);
    }

    private void updateTipTotalPerPerson() {

        double percentDbl = percent / 100;

        DecimalFormat decFormat = new DecimalFormat("0.00");
        decFormat.setRoundingMode(RoundingMode.HALF_EVEN);

        tip = billAmount * percentDbl;
        textViewTipValue.setText(getResources().getString(R.string.dollar_sign) + decFormat.format(tip));
        total = billAmount + tip;
        textViewTotalValue.setText(getResources().getString(R.string.dollar_sign) + decFormat.format(total));

        if (spinnerSplit.getSelectedItemPosition() == 1) {
            tipPerPerson = tip / 2;
            textViewPerPersonTipValue.setText(getResources().getString(R.string.dollar_sign) + decFormat.format(tipPerPerson));
            totalPerPerson = total / 2;
            textViewPerPersonTotalValue.setText(getResources().getString(R.string.dollar_sign) + decFormat.format(totalPerPerson));
        }
        else if (spinnerSplit.getSelectedItemPosition() == 2){
            tipPerPerson = tip / 3;
            textViewPerPersonTipValue.setText(getResources().getString(R.string.dollar_sign) + decFormat.format(tipPerPerson));
            totalPerPerson = total / 3;
            textViewPerPersonTotalValue.setText(getResources().getString(R.string.dollar_sign) + decFormat.format(totalPerPerson));
        }
        else if (spinnerSplit.getSelectedItemPosition() == 3) {
            tipPerPerson = tip / 4;
            textViewPerPersonTipValue.setText(getResources().getString(R.string.dollar_sign) + decFormat.format(tipPerPerson));
            totalPerPerson = total / 4;
            textViewPerPersonTotalValue.setText(getResources().getString(R.string.dollar_sign) + decFormat.format(totalPerPerson));
        }
        else {
            return;
        }
    }

    /*private class billAmountEditorListener implements EditText.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEND || event.getAction() == KeyEvent.ACTION_DOWN) {

                String billAmountString = editTextBillAmount.getText().toString();
                billAmount = Double.parseDouble(billAmountString);
                updateTipTotalPerPerson();
                return true;
            }
            return false;
        }
    }*/
    private class BillAmountTextWatcher implements TextWatcher {
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String billAmountString = editTextBillAmount.getText().toString();
            if (billAmountString.length() > 0) {
                billAmount = Double.parseDouble(billAmountString);
            } else {
                billAmount = 0.00;
            }
            updateTipTotalPerPerson();
        }
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
        @Override
        public void afterTextChanged(Editable editable) { }
    }

    private class percentSeekBarListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            textViewPercentValue.setText(progress + getResources().getString(R.string.percent_sign));
            percent = progress;
            updateTipTotalPerPerson();
        }


        public void onStartTrackingTouch(SeekBar seekBar) {

        }


        public void onStopTrackingTouch (SeekBar seekBar) {

        }
    }

    private class spinnerSplitListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position == 0){
                textViewPerPersonTipValue.setVisibility(View.GONE);
                textViewPerPersonTip.setVisibility(View.GONE);
                textViewPerPersonTotalValue.setVisibility(View.GONE);
                textViewPerPersonTotal.setVisibility(View.GONE);
                textViewPerPerson.setVisibility(View.GONE);
                updateTipTotalPerPerson();
            }
            else {
                textViewPerPersonTipValue.setVisibility(View.VISIBLE);
                textViewPerPersonTip.setVisibility(View.VISIBLE);
                textViewPerPersonTotalValue.setVisibility(View.VISIBLE);
                textViewPerPersonTotal.setVisibility(View.VISIBLE);
                textViewPerPerson.setVisibility(View.VISIBLE);
                updateTipTotalPerPerson();
            }
        }

        public void onNothingSelected(AdapterView<?> parent) {

        }

    }
}
