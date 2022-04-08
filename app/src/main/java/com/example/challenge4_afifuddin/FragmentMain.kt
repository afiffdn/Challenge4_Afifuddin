package com.example.challenge4_afifuddin

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.challenge4_afifuddin.database.recipe.Resep
import com.example.challenge4_afifuddin.database.recipe.ResepAdapter
import com.example.challenge4_afifuddin.database.recipe.ResepDatabase
import com.example.challenge4_afifuddin.databinding.AddDataBinding
import com.example.challenge4_afifuddin.databinding.EditDataBinding
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
    override fun onResume() {
        super.onResume()
        fetchData()
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

    @OptIn(InternalCoroutinesApi::class)
    private fun fetchData() {
        lifecycleScope.launch(Dispatchers.IO){
            val listResep = mDb?.resepdao()?.getAllRecipe()
            activity?.runOnUiThread(){
            listResep?.let {
                val adapter = ResepAdapter(
                    it,
                    onEditClickListener = {resep ->
                        val editdialog = EditDataBinding.inflate(LayoutInflater.from(requireContext()))
                        val dialogBuilder = AlertDialog.Builder(requireContext())
                        dialogBuilder.setView(editdialog.root)
                        val dialog = dialogBuilder.create()
                        dialog.setCancelable(false)
                        editdialog.etInsertFoodName.setText(resep.name)
                        editdialog.etInsertIngredient.setText(resep.ingredient)
                        editdialog.etInsertStep.setText(resep.step)
                        editdialog.btnCancel.setOnClickListener {
                            dialog.dismiss()
                        }

                        editdialog.btnEdit.setOnClickListener {
                        val myrecipe = ResepDatabase.getInstance(requireContext())
                            val resep = Resep(
                                null,
                                editdialog.etInsertFoodName.text.toString(),
                                editdialog.etInsertIngredient.text.toString(),
                                editdialog.etInsertStep.text.toString()
                            )
                            lifecycleScope.launch(Dispatchers.IO){
                            val result = myrecipe?.resepdao()?.updateRecipe(resep)
                                runBlocking  (Dispatchers.Main){
                                    if (result != 0){
                                        Toast.makeText(
                                            requireContext(),
                                            "resep ${resep.name} berhasil di update",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                            fetchData()
                                    }else{
                                        Toast.makeText(
                                            requireContext(),
                                            "resep ${resep.name} gagal di update",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                        dialog.dismiss()
                                    }
                                }
                            }

                        }

                        dialog.show()
                    },
                    onDeleteClickListener ={resep ->
                        AlertDialog.Builder(requireContext())
                            .setPositiveButton("Iya"){_,_->
                        val mDb = ResepDatabase.getInstance(requireContext())
                                lifecycleScope.launch(Dispatchers.IO){
                                val result = mDb?.resepdao()?.deleteRecipe(resep)
                                    activity?.runOnUiThread{
                                        if(result != 0){
                                            Toast.makeText(
                                                requireContext(),
                                                "${resep.name} berhasil dihapus",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        } else {
                                            Toast.makeText(
                                                requireContext(),
                                                "${resep.name} gagal dihapus",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                    fetchData()
                                }
                            }
                            .setNegativeButton("Tidak"){dialog, _ ->dialog.dismiss()
                            }
                            .setMessage("Apakah anda akan menghapus resep ${resep.name}")
                            .setTitle("konfirmasi hapus")
                            .create()
                            .show()
                    }


                )
                binding.recycleview.adapter = adapter

            }
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        ResepDatabase.destroyInstance()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}