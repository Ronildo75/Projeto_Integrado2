package com.projetopi.projetopidepizza

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.projetopi.projetopidepizza.databinding.ActivityProductDetailsBinding
import java.text.DecimalFormat

class ProductDetails : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailsBinding
    var amount = 1

    companion object {
        const val EXTRA_NAME = "name"
        const val EXTRA_AMOUNT = "amount"
        const val EXTRA_TOTAL = "total"
        const val EXTRA_DRINKS = "drinks"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.parseColor("#E0E0E0")

        val imgProduct: Int = intent.extras?.getInt("imgProduct") ?: 0
        val name: String? = intent.extras?.getString("name")
        val price: Double = intent.extras?.getString("price")?.toDouble() ?: 0.0
        var newPrice = price
        val decimalFormat = DecimalFormat.getCurrencyInstance()

        binding.imgProduct.setBackgroundResource(imgProduct)
        binding.txtProductName.text = name
        binding.txtPrice.text = decimalFormat.format(price)

        binding.btBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btIncrease.setOnClickListener {
            if (amount < 9) {
                amount++
                binding.txtAmount.text = amount.toString()
                newPrice += price
                binding.txtPrice.text = decimalFormat.format(newPrice)
            }
        }

        binding.btToDecrease.setOnClickListener {
            if (amount > 1) {
                amount--
                binding.txtAmount.text = amount.toString()
                newPrice -= price
                binding.txtPrice.text = decimalFormat.format(newPrice)
            }
        }

        binding.btConfirm.setOnClickListener {
            val drinks: String = when {
                binding.btCocaCola.isChecked -> "Coca-Cola"
                binding.btFanta.isChecked -> "Fanta"
                binding.btPepsi.isChecked -> "Pepsi"
                binding.btSuco.isChecked -> "Suco de Laranja"
                else -> ""
            }

            val intent = Intent(this, Payment::class.java).apply {
                putExtra(EXTRA_NAME, name)
                putExtra(EXTRA_AMOUNT, amount)
                putExtra(EXTRA_TOTAL, newPrice)
                putExtra(EXTRA_DRINKS, drinks)
            }
            startActivity(intent)
            finish()
        }
    }
}
