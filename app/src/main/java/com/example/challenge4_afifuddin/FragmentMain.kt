package com.example.challenge4_afifuddin

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.challenge4_afifuddin.database.LoginDatabase
import com.example.challenge4_afifuddin.database.recipe.Resep
import com.example.challenge4_afifuddin.database.recipe.ResepDatabase
import com.example.challenge4_afifuddin.databinding.AddDataBinding
import com.example.challenge4_afifuddin.databinding.FragmentMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class FragmentMain : Fragment() {
    private var _binding : FragmentMainBinding? = null
    private val binding get() = _binding!!
    private var mDb: ResepDatabase? = null
    private  val sharePredfile = "kotlinsharepreference"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    @OptIn(InternalCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDb = ResepDatabase.getInstance(requireContext())
        val sharedPreferences =
            requireContext().getSharedPreferences(sharePredfile, Context.MODE_PRIVATE)

        val name = sharedPreferences.getString("username","apip")
        binding.tvHello.setText("Hello,${name}")

        val editor :SharedPreferences.Editor = sharedPreferences.edit()
        binding.logout.setOnClickListener {
            editor.clear()
            editor.apply()
            findNavController().navigate(R.id.action_fragmentMain_to_loginFragment)
        }
        binding.ibAdd.setOnClickListener {
            val addDialog = AddDataBinding.inflate(LayoutInflater.from(requireContext()))
            val dialogBuilder = AlertDialog.Builder(requireContext())
            dialogBuilder.setView(addDialog.root)
            val dialog = dialogBuilder.create()
            dialog.setCancelable(false)
            addDialog.btnCancel.setOnClickListener{
                dialog.dismiss()
            }
            addDialog.btnAdd.setOnClickListener {
                val resep = Resep(
                    null,
                    addDialog.etInsertFoodName.text.toString(),
                    addDialog.etInsertIngredient.text.toString(),
                    addDialog.etInsertStep.text.toString()
                )
                    lifecycleScope.launch(Dispatchers.IO){
                        val insert = mDb?.resepdao()?.insertRecipe(resep)
                        runBlocking(Dispatchers.Main){
                        if (insert != 0.toLong()){
                            Toast.makeText(requireContext(), "Resep ${resep.name} berhasil di tambahkan", Toast.LENGTH_SHORT)
                                .show()
                                fetchData()
                                dialog.dismiss()
                        }else
                        {
                            Toast.makeText(requireContext(), "Resep ${resep.name} gagal di tambahkan", Toast.LENGTH_SHORT)
                                .show()
                            fetchData()
                            dialog.dismiss()
                        }
                        }
                    }

            }
            dialog.show()
        }


    }

    private fun fetchData() {
        lifecycleScope.launch(Dispatchers.IO){
            val listResep = mDb?.resepdao()?.getAllRecipe()
            activity?.runOnUiThread(){
            listResep?.let {

            }
            }
        }
    }

}