package com.example.roomimage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.db.model.entity.Product
import com.example.roomimage.app.ProductApplication
import com.example.roomimage.databinding.FragmentFirstBinding
import com.example.roomimage.viewmodel.ProductViewModel
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private val viewModel: ProductViewModel by viewModels {
        ProductViewModel.ProductViewModelFactory((this@FirstFragment.requireActivity().application as ProductApplication).repository)
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonList.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_itemFragment)
        }

        binding.buttonAdd.setOnClickListener {
            insertProductToDB()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun insertProductToDB() {
        val name: String = binding.nameInput.editText?.text.toString()
        val desc: String = binding.descInput.editText?.text.toString()
        val price: String = binding.priceInput.editText?.text.toString()
        //val url: String = binding.imageView.
        val url: String = "https://ih1.redbubble.net/image.1911138500.5263/flat,750x,075,f-pad,750x1000,f8f8f8.jpg"

        if (inputValidation(name, desc, price, url)) {
            val priceFloat = price.toFloat()
            val product: Product = Product(0, name, desc, url, priceFloat)
            viewModel.insert(product)
            Snackbar.make(binding.root, "Product added", Snackbar.LENGTH_LONG).show()
        } else {
            Snackbar.make(binding.root, "Please make sure fields are filled", Snackbar.LENGTH_LONG).show()
        }

    }

    fun inputValidation(name: String, desc: String, price: String, url: String): Boolean {
        return name != "" && desc != "" && price != "" && url != ""
    }
}