package com.devin.project4_v5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.devin.project4_v5.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

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

        //connect to database
        val db = DataBaseHandler(this.requireContext())

        //add food to db
        binding.add.setOnClickListener {
            val userFood = Foods()
            userFood.foodAmountName = binding.food.text.toString()
            db.insertData(userFood)
            binding.food.text.clear()
        }

        //delete food from db
        binding.delete.setOnClickListener {
            var foodName = binding.food.text.toString()
            if(db.deleteData(foodName)>0) {
                Toast.makeText(context, "Deleted item $foodName", Toast.LENGTH_LONG).show()
            }
            binding.food.text.clear()
        }

        //read food data
        binding.vwItems.setOnClickListener {
            val foodList = db.readData()
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundleOf("myList" to foodList))
        }

        //lookup data about food from api
        binding.lookup.setOnClickListener {
            var foodName = binding.food.text.toString()
            findNavController().navigate(R.id.action_FirstFragment_to_ThirdFragment, bundleOf("myList" to foodName))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}