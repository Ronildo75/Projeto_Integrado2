package com.projetopi.projetopidepizza.adapter

import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projetopi.projetopidepizza.ProductDetails
import com.projetopi.projetopidepizza.databinding.ProductItemBinding
import com.projetopi.projetopidepizza.model.Product

class ProductAdapter(private val context: Context, private val productList: MutableList<Product>):
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val listItem = ProductItemBinding.inflate(android.view.LayoutInflater.from(context), parent, false)
        return ProductViewHolder(listItem)
    }

    override fun getItemCount() = productList.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.imgProduct.setBackgroundResource(productList[position].imgProduct)
        holder.txtName.text = productList[position].name
        holder.txtPrice.text = productList[position].price
        holder.btAdd.setOnClickListener {
            val intent = Intent(context, ProductDetails::class.java)
            intent.putExtra("imgProduct", productList[position].imgProduct)
            intent.putExtra("name", productList[position].name)
            intent.putExtra("price", productList[position].price)
            context.startActivity(intent)

        }

    }
    inner class ProductViewHolder(binding: ProductItemBinding): RecyclerView.ViewHolder(binding.root) {
        val imgProduct = binding.imgProduct
        val txtName = binding.txtName
        val txtPrice = binding.txtPrice
        val btAdd = binding.btAdd

    }

}