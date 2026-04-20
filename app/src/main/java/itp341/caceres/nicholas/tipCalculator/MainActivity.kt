package itp341.caceres.nicholas.tipCalculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.EditText
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.math.RoundingMode
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
  private lateinit var textViewPerPersonTipValue: TextView
  private lateinit var textViewPerPersonTip: TextView
  private lateinit var textViewPerPersonTotalValue: TextView
  private lateinit var textViewPerPersonTotal: TextView
  private lateinit var textViewPerPerson: TextView
  private lateinit var textViewTotalValue: TextView
  private lateinit var textViewTipValue: TextView
  private lateinit var textViewPercentValue: TextView

  private lateinit var spinnerSplit: Spinner

  private lateinit var seekBarPercent: SeekBar

  private lateinit var editTextBillAmount: EditText

  private var billAmount = 0.0
  private var percent = 0.0
  private var tip = 0.0
  private var tipPerPerson = 0.0
  private var total = 0.0
  private var totalPerPerson = 0.0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    textViewPercentValue = findViewById(R.id.text_percent_value)
    textViewTipValue = findViewById(R.id.text_tip_value)
    textViewTotalValue = findViewById(R.id.text_total_value)
    textViewPerPersonTipValue = findViewById(R.id.text_per_person_tip_value)
    textViewPerPersonTip = findViewById(R.id.text_per_person_tip)
    textViewPerPersonTotalValue = findViewById(R.id.text_per_person_total_value)
    textViewPerPersonTotal = findViewById(R.id.text_per_person_total)
    textViewPerPerson = findViewById(R.id.text_per_person)

    spinnerSplit = findViewById(R.id.spinnerSplit)

    seekBarPercent = findViewById(R.id.seek_bar)
    percent = 15.00

    editTextBillAmount = findViewById(R.id.edit_bill_amount)

    //billAmountEditorListener billAmountListener = new billAmountEditorListener();
    //editTextBillAmount.setOnEditorActionListener(billAmountListener);
    editTextBillAmount.addTextChangedListener(BillAmountTextWatcher())
    val percentSeekListener = percentSeekBarListener()
    seekBarPercent.setOnSeekBarChangeListener(percentSeekListener)
    val splitListener = spinnerSplitListener()
    spinnerSplit.setOnItemSelectedListener(splitListener)
  }

  private fun updateTipTotalPerPerson() {
    val percentDbl = percent / 100

    val decFormat = DecimalFormat("0.00")
    decFormat.setRoundingMode(RoundingMode.HALF_EVEN)

    tip = billAmount * percentDbl
    textViewTipValue.setText(getResources().getString(R.string.dollar_sign) + decFormat.format(tip))
    total = billAmount + tip
    textViewTotalValue.setText(
      getResources().getString(R.string.dollar_sign) + decFormat.format(
        total
      )
    )

    if (spinnerSplit.getSelectedItemPosition() == 1) {
      tipPerPerson = tip / 2
      textViewPerPersonTipValue.setText(
        getResources().getString(R.string.dollar_sign) + decFormat.format(
          tipPerPerson
        )
      )
      totalPerPerson = total / 2
      textViewPerPersonTotalValue.setText(
        getResources().getString(R.string.dollar_sign) + decFormat.format(
          totalPerPerson
        )
      )
    } else if (spinnerSplit.getSelectedItemPosition() == 2) {
      tipPerPerson = tip / 3
      textViewPerPersonTipValue.setText(
        getResources().getString(R.string.dollar_sign) + decFormat.format(
          tipPerPerson
        )
      )
      totalPerPerson = total / 3
      textViewPerPersonTotalValue.setText(
        getResources().getString(R.string.dollar_sign) + decFormat.format(
          totalPerPerson
        )
      )
    } else if (spinnerSplit.getSelectedItemPosition() == 3) {
      tipPerPerson = tip / 4
      textViewPerPersonTipValue.setText(
        getResources().getString(R.string.dollar_sign) + decFormat.format(
          tipPerPerson
        )
      )
      totalPerPerson = total / 4
      textViewPerPersonTotalValue.setText(
        getResources().getString(R.string.dollar_sign) + decFormat.format(
          totalPerPerson
        )
      )
    } else {
      return
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
  private inner class BillAmountTextWatcher : TextWatcher {
    override fun onTextChanged(charSequence: CharSequence?, i: Int, i1: Int, i2: Int) {
      val billAmountString = editTextBillAmount.getText().toString()
      if (billAmountString.length > 0) {
        billAmount = billAmountString.toDouble()
      } else {
        billAmount = 0.00
      }
      updateTipTotalPerPerson()
    }

    override fun beforeTextChanged(charSequence: CharSequence?, i: Int, i1: Int, i2: Int) {}
    override fun afterTextChanged(editable: Editable?) {}
  }

  private inner class percentSeekBarListener : OnSeekBarChangeListener {
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
      textViewPercentValue.setText(progress.toString() + getResources().getString(R.string.percent_sign))
      percent = progress.toDouble()
      updateTipTotalPerPerson()
    }


    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }


    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }
  }

  private inner class spinnerSplitListener : OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
      if (position == 0) {
        textViewPerPersonTipValue.setVisibility(View.GONE)
        textViewPerPersonTip.setVisibility(View.GONE)
        textViewPerPersonTotalValue.setVisibility(View.GONE)
        textViewPerPersonTotal.setVisibility(View.GONE)
        textViewPerPerson.setVisibility(View.GONE)
        updateTipTotalPerPerson()
      } else {
        textViewPerPersonTipValue.setVisibility(View.VISIBLE)
        textViewPerPersonTip.setVisibility(View.VISIBLE)
        textViewPerPersonTotalValue.setVisibility(View.VISIBLE)
        textViewPerPersonTotal.setVisibility(View.VISIBLE)
        textViewPerPerson.setVisibility(View.VISIBLE)
        updateTipTotalPerPerson()
      }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
  }
}
