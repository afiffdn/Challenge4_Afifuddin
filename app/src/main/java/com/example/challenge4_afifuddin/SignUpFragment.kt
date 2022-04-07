package com.example.challenge4_afifuddin

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import  android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.challenge4_afifuddin.database.Login
import com.example.challenge4_afifuddin.database.LoginDatabase
import com.example.challenge4_afifuddin.databinding.FragmentSignUpBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class SignUpFragment : Fragment() {
    private var _binding : FragmentSignUpBinding? = null
    private val binding get() =_binding!!
    private  val sharePredfile = "kotlinsharepreference"
    private var mDb: LoginDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root


//        val c = Calendar.getInstance()
//        val year = c.get(Calendar.YEAR)
//        val month = c.get(Calendar.MONTH)
//        val day = c.get(Calendar.DAY_OF_MONTH)
//        binding.tglLahir.setOnClickListener {
//            val date = DatePickerDialog(requireContext(), { _, mYear, mMonth, mDay ->
//                binding.etDate.setText("$mDay/$mMonth/$mYear") },year,month,day)
//            date.show()
//        }
   }



    @SuppressLint("CommitPrefEdits")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        mDb = LoginDatabase.getInstance(requireContext())
        val sharedPreferences =
            requireContext().getSharedPreferences(sharePredfile, Context.MODE_PRIVATE)

        binding.btnSignup2.setOnClickListener {

            val login = Login(
                binding.etUsernameSignUp.text.toString(),
                binding.etPasswordSignup.text.toString(),
                binding.etEmail.text.toString(),
            binding.etName.text.toString()


            )


            if (binding.etName.text.isNullOrEmpty()){   binding.etName.error = "Masukkan Nama"}
            else if (binding.etEmail.text.isNullOrEmpty()){binding.etEmail.error = "Masukkan Tanggal Lahir"}
            else if (binding.etUsernameSignUp.text.isNullOrEmpty()){  binding.etUsernameSignUp.error = "Masukkan Username"}
            else if (binding.etPasswordSignup.text.isNullOrEmpty()){ binding.etPasswordSignup.error = "Masukkan Password"}
            else {
                lifecycleScope.launch(Dispatchers.IO){
                    val result = mDb?.loginDao()?.insertLogin(login)
                    runBlocking(Dispatchers.Main){
                        if (result != 0.toLong()){
                            val editor :SharedPreferences.Editor =sharedPreferences.edit()
                            editor.putString("registusername",binding.etUsernameSignUp.text.toString())
                            editor.apply()
                            view.findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
                            Toast.makeText(requireContext(), "sukses tambah data", Toast.LENGTH_SHORT).show()
                        }

                    }
                }
            }
        }
    }
}


