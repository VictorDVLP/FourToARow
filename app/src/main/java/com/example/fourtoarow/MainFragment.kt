package com.example.fourtoarow

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.fourtoarow.databinding.MainFragmentBinding
import com.example.fourtoarow.databinding.WinnerBinding

class MainFragment : Fragment() {

    private val viewModel = MainViewModel()

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val arrayArrows = arrayOf(
            binding.arrow1, binding.arrow2, binding.arrow3, binding.arrow4,
            binding.arrow5, binding.arrow6, binding.arrow7
        )
        viewModel.setArrayArrows(arrayArrows)

        val arrayBoard = arrayOf(
            arrayOf(
                Gap(0, binding.box00),
                Gap(0, binding.box01),
                Gap(0, binding.box02),
                Gap(0, binding.box03),
                Gap(0, binding.box04),
                Gap(0, binding.box05)
            ),
            arrayOf(
                Gap(0, binding.box06),
                Gap(0, binding.box07),
                Gap(0, binding.box08),
                Gap(0, binding.box09),
                Gap(0, binding.box10),
                Gap(0, binding.box11)
            ),
            arrayOf(
                Gap(0, binding.box12),
                Gap(0, binding.box13),
                Gap(0, binding.box14),
                Gap(0, binding.box15),
                Gap(0, binding.box16),
                Gap(0, binding.box17)
            ),
            arrayOf(
                Gap(0, binding.box18),
                Gap(0, binding.box19),
                Gap(0, binding.box20),
                Gap(0, binding.box21),
                Gap(0, binding.box22),
                Gap(0, binding.box23)
            ),
            arrayOf(
                Gap(0, binding.box24),
                Gap(0, binding.box25),
                Gap(0, binding.box26),
                Gap(0, binding.box27),
                Gap(0, binding.box28),
                Gap(0, binding.box29)
            ),
            arrayOf(
                Gap(0, binding.box30),
                Gap(0, binding.box31),
                Gap(0, binding.box32),
                Gap(0, binding.box33),
                Gap(0, binding.box34),
                Gap(0, binding.box35)
            ),
            arrayOf(
                Gap(0, binding.box36),
                Gap(0, binding.box37),
                Gap(0, binding.box38),
                Gap(0, binding.box39),
                Gap(0, binding.box40),
                Gap(0, binding.box41)
            ),
        )
        viewModel.setArrayBoards(arrayBoard)

        arrayArrows.apply {
            viewModel.clickArrow(this, arrayBoard)
        }

        viewModel.booleanAction.observe(viewLifecycleOwner) { boo -> if (boo == true) { winnerNotice(boo) } }

        binding.resetMarket.setOnClickListener { viewModel.resetMarket() }
        binding.resetButton.setOnClickListener { viewModel.resetGame() }
    }

    private fun winnerNotice(booleanAction: Boolean) {

        if (booleanAction) {

            val alertBuilder = Dialog(requireContext())
            val alertDialogBinding = WinnerBinding.inflate(LayoutInflater.from(requireActivity()))
            alertBuilder.requestWindowFeature(Window.FEATURE_NO_TITLE)
            alertBuilder.setContentView(alertDialogBinding.root)
            alertBuilder.setCancelable(false)
            alertBuilder.window?.setLayout(800, WindowManager.LayoutParams.WRAP_CONTENT)


            val btnReturnDialog = alertDialogBinding.buttonDialog
            val textDialog = alertDialogBinding.winDialogBack
            val titleDialog = alertDialogBinding.winDialogTitle

            if (viewModel.turn.value == 2) {
                btnReturnDialog.backgroundTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.red)
                textDialog.text = getString(R.string._1_s_gana, "Red")
                titleDialog.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.red_dialog)
                titleDialog.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
            } else {
                btnReturnDialog.backgroundTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.yellow)
                textDialog.text = getString(R.string._1_s_gana, "Yellow")
                titleDialog.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.yellow_dialog)
                titleDialog.setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow))
            }

            titleDialog.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

            btnReturnDialog.setOnClickListener {
                alertBuilder.dismiss()
                viewModel.resetGame()
            }
            alertBuilder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertBuilder.show()
        }
    }
}

