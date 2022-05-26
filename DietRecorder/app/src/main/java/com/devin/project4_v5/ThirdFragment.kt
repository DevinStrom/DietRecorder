package com.devin.project4_v5

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.android.volley.toolbox.Volley
import com.devin.project4_v5.databinding.FragmentThirdBinding
import com.squareup.picasso.Picasso

class ThirdFragment : Fragment() {

    companion object {
        fun newInstance() = ThirdFragment()
    }

    private lateinit var binding: FragmentThirdBinding
    private lateinit var viewModel: ThirdViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root
    }

    //observers to get food data
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ThirdViewModel::class.java)
        var foodName = arguments?.getString("myList").toString()

        println("from thirdfragment: $foodName") //kept for debugging

        var queue = Volley.newRequestQueue(context)
        viewModel.setFood(foodName, queue)

        val foodLabelObserver = Observer<String>{food-> binding.foodLabel.text = food  }
        viewModel.getFoodName().observe(viewLifecycleOwner, foodLabelObserver)

        val calObserver = Observer<String>{cal-> binding.enercKCal.text = cal  }
        viewModel.getCal().observe(viewLifecycleOwner, calObserver)

        val fatObserver = Observer<String>{fat-> binding.fat.text = fat  }
        viewModel.getFat().observe(viewLifecycleOwner, fatObserver)

        val fiberObserver = Observer<String>{fiber-> binding.fibtg.text = fiber  }
        viewModel.getFiber().observe(viewLifecycleOwner, fiberObserver)

        val carbObserver = Observer<String>{carbs-> binding.chocdf.text = carbs  }
        viewModel.getCarbs().observe(viewLifecycleOwner, carbObserver)

        val iconObserver = Observer<String> {icon ->
            Picasso.with(context).load(icon).into(binding.foodIcon)
        }
        viewModel.getIcon().observe(viewLifecycleOwner, iconObserver)
    }

}