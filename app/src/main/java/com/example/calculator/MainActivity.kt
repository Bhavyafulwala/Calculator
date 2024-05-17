package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.calculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var lastnumeric=false
    var stateerroe=false
    var lastdot=false

    private lateinit var expression: Expression
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

   fun onallclear(view: View){

       binding.entervalue.text=""
       binding.ansvalue.text=""
       lastnumeric=false
       lastdot=false
       stateerroe=false
       binding.ansvalue.visibility=View.GONE

   }
    fun onequals(view: View){
        equals()
        binding.entervalue.text=binding.ansvalue.text.toString().drop(1)
    }

    fun equals(){
        if(lastnumeric && !stateerroe){
            val txt=binding.entervalue.text.toString()
            expression=ExpressionBuilder(txt).build()
            try {
                val result=expression.evaluate()
                binding.ansvalue.visibility=View.VISIBLE
                binding.ansvalue.text="="+result.toString()
            }
            catch (ex:ArithmeticException){
                Log.e("evaluateerroe",ex.toString())
                binding.ansvalue.text="Error"
                stateerroe=true
                lastnumeric=false
            }
        }
    }


    fun ondigit(view: View){
        if(stateerroe){
            binding.entervalue.text=(view as Button).text
            stateerroe=false
        }else{
            binding.entervalue.append((view as Button).text)
        }
        lastnumeric=true
        equals()
    }

    fun onoperator(view: View){
        if(!stateerroe && lastnumeric){
            binding.entervalue.append((view as Button).text)
            lastdot=false
            lastnumeric=false
            equals()
        }
    }


    fun onclear(view: View){
        binding.entervalue.text=""
        lastnumeric=false
    }


    fun oncancel(view: View){
        binding.entervalue.text=binding.entervalue.text.dropLast(1)
        try {
            val lastchar=binding.entervalue.text.toString().last()
            if(lastchar.isDigit()){
                equals()
            }
        }catch (ex:Exception){
            binding.ansvalue.text=""
            binding.ansvalue.visibility=View.GONE
            Log.e("error",toString())
        }
    }

}