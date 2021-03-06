package com.vanchi.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.vanchi.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var lastNumeric : Boolean = false
    var lastDecimal : Boolean = false
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnClear.setOnClickListener {
            clear(it)
        }
    }

    fun onEqual(view: View) {
        var input = binding.input.text
        var negativeInput = false
        if(input.startsWith("-")){
            negativeInput = true
            input = input.substring(1)
        }
        var inputs = input.split("*","/","+", "-")
        var left = inputs[0]
        var right = inputs[1]
        if(input.contains("*")){
            var result = left.toDouble() * right.toDouble()
            if(negativeInput){
                result *=-1
            }
            binding.input.setText(result.toString())
        }else if(input.contains("+")){
            var result = left.toDouble() + right.toDouble()
            if(negativeInput){
                result = (-left.toDouble()) + right.toDouble()
            }
            binding.input.setText(result.toString())
        }else if(input.contains("-")){
            var result = left.toDouble() - right.toDouble()
            if(negativeInput){
                result = (-left.toDouble()) - right.toDouble()
            }
            binding.input.setText(result.toString())
        }else if(input.contains("/")){
            var result = left.toDouble() / right.toDouble()
            if(negativeInput){
                result = (-left.toDouble()) / right.toDouble()
            }
            binding.input.setText(result.toString())
        }
    }

    fun clear(view: View){
        binding.input.text = ""
        lastNumeric = false
        lastDecimal = false
    }

    fun onDigit(view: View) {
        binding.input.append((view as Button).text)
        lastNumeric = true
        lastDecimal = false
    }

    fun onDecimal(view: View){
        if(lastNumeric && !lastDecimal){
            binding.input.append(".")
            lastNumeric = false
            lastDecimal = true
        }
    }

    fun onOperator(view: View){
        if(lastNumeric && !hasOperator(binding.input.text.toString())){
            binding.input.append((view as Button).text)
            lastNumeric = false
            lastDecimal = false
        }

        if((view as Button).text.equals("-") && binding.input.text.length == 0){
            binding.input.append("-")
        }
    }

    fun hasOperator(value : String) : Boolean {
        return if(value.startsWith("-") && !(value.subSequence(1,value.length).contains("/")
                    ||value.subSequence(1,value.length).contains("-")
                    ||value.subSequence(1,value.length).contains("+")
                    ||value.subSequence(1,value.length).contains("*"))){
            false
        }else{
            (value.contains("/")
                    ||value.contains("-")
                    ||value.contains("+")
                    ||value.contains("*"))
        }
    }
}