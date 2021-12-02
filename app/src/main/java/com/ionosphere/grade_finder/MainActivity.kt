package com.ionosphere.grade_finder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ionosphere.grade_finder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener {
            
            // Retreiving taxt field values
            val marksInPhysics: Int? = binding.inputTextPhysics.text.toString().toIntOrNull();
            val marksInPChemistry: Int? = binding.inputTextChemistry.text.toString().toIntOrNull();
            val marksInBiology: Int? = binding.inputTextBiology.text.toString().toIntOrNull();

            // Checking if any of the field left empty
            if (marksInPhysics == null || marksInPChemistry == null || marksInBiology == null) {

                Toast
                    .makeText(this,
                        "None of the mark fields can be left empty!",
                        Toast.LENGTH_SHORT)
                    .show()

                return@setOnClickListener
            }

            // Checking if any of the value is not in range (0-100)
            if (marksInPhysics < 0 || marksInPhysics > 100 ||
                marksInPChemistry < 0 || marksInPChemistry > 100 ||
                marksInBiology < 0 || marksInBiology > 100) {

                Toast
                    .makeText(this,
                        "None of the marks can be less than 0 or more than 100",
                        Toast.LENGTH_SHORT)
                    .show()

                return@setOnClickListener
            }

            // It stores total grade point
            var totalGradePoint = determineGradePoint(marksInPhysics) +
                    determineGradePoint(marksInPChemistry) + determineGradePoint(marksInBiology)

            // Accumalting point obtained for disciolinary from the radio buttons
            totalGradePoint += when (binding.radioGroupDisc.checkedRadioButtonId) {
                R.id.option_good -> 3
                R.id.option_moderate -> 2
                else -> 1
            }

            // Checking if homework 1 was completed
            if (binding.homework1.isChecked) {
                totalGradePoint += 1
            }

            // Checking if homework 2 was completed
            if (binding.homework2.isChecked) {
                totalGradePoint += 1
            }

            binding.resultHeading.visibility = View.VISIBLE

            // Generating and making the final result visible
            binding.finalResult.text = determineFinalGrade(totalGradePoint)
            binding.finalResult.visibility = View.VISIBLE
            
            // Forcing the view to focus at the final result when generated
            binding.scrollView.post {
                binding.scrollView.fullScroll(View.FOCUS_DOWN)
            }
        }
    }
    
    // Function for determining grade point for an individual subject based on mark
    private fun determineGradePoint(marks: Int): Int {

        return when (marks) {

            in (80..100) -> 5
            in (70..79) -> 4
            in (60..69) -> 3
            in (50..59) -> 2
            in (33..49) -> 1
            else -> 0
        }
    }

    // Function for determining final grade based on accumulated total grade point
    private fun determineFinalGrade(totalPoint: Int): String {

        return when (totalPoint) {

            in (18..20) -> "A+"
            in (15..17) -> "A"
            in (12..14) -> "A-"
            in (10..11) -> "B"
            in (8..9) -> "C"
            in (5..7) -> "D"
            else -> "F"
        }
    }
}