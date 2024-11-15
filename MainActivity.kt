package com.projetopi.projetopidepizza

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.projetopi.projetopidepizza.adapter.ProductAdapter
import com.projetopi.projetopidepizza.databinding.ActivityMainBinding
import com.projetopi.projetopidepizza.listitems.Products
import com.projetopi.projetopidepizza.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    // Declaração das variáveis de binding e adaptador
    private lateinit var binding: ActivityMainBinding
    private lateinit var productAdapter: ProductAdapter
    private val products = Products()
    private val productList: MutableList<Product> = mutableListOf()
    private val originalProductList: MutableList<Product> = mutableListOf()

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Define a cor da barra de status
        window.statusBarColor = Color.parseColor("#E0E0E0")

        // Carrega os produtos em uma coroutine
        CoroutineScope(Dispatchers.IO).launch {
            products.getProducts().collect {
                originalProductList.addAll(it) // Armazena a lista original de produtos
                productList.addAll(it) // Inicializa a lista de produtos exibida
                runOnUiThread {
                    productAdapter.notifyDataSetChanged() // Notifica o adaptador sobre mudanças
                }
            }
        }

        // Configura o RecyclerView
        val recyclerViewProducts = binding.recyclerViewProducts
        recyclerViewProducts.layoutManager = GridLayoutManager(this, 2)
        productAdapter = ProductAdapter(this, productList)
        recyclerViewProducts.setHasFixedSize(true)
        recyclerViewProducts.adapter = productAdapter

        // Configura os listeners dos botões
        setupButtonClickListeners()
    }

    // Função para filtrar produtos por categoria
    private fun filterProducts(category: String) {
        val filteredList = if (category == "Todos") {
            originalProductList
        } else {
            originalProductList.filter { it.category == category }
        }
        productList.clear()
        productList.addAll(filteredList)
        productAdapter.notifyDataSetChanged()
    }

    // Função para atualizar o estilo dos botões
    private fun updateButtonStyles(selectedButton: View) {
        val buttons = listOf(binding.btBebidas, binding.btSalgados, binding.btPizza, binding.btDoces)
        buttons.forEach { button ->
            if (button == selectedButton) {
                button.setBackgroundResource(R.drawable.bg_button_enabled)
                button.setTextColor(Color.WHITE)
            } else {
                button.setBackgroundResource(R.drawable.bg_button_disabled)
                button.setTextColor(R.color.dark_gray)
            }
        }
    }

    // Função para configurar os listeners dos botões
    private fun setupButtonClickListeners() {
        val buttons = listOf(binding.btBebidas, binding.btSalgados, binding.btPizza, binding.btDoces)
        buttons.forEach { button ->
            button.setOnClickListener {
                val category = when (button) {
                    binding.btBebidas -> "Bebidas"
                    binding.btSalgados -> "Salgados"
                    binding.btPizza -> "Pizza"
                    binding.btDoces -> "Doces"
                    else -> ""
                }
                filterProducts(category) // Filtra os produtos pela categoria selecionada
                updateButtonStyles(button) // Atualiza o estilo do botão selecionado
                binding.txtListTitle.text = category // Atualiza o título da lista
            }
        }
    }
}
