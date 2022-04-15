package com.example.challenge4_afifuddin

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.challenge4_afifuddin.database.recipe.AppDatabase
import com.example.challenge4_afifuddin.databinding.FragmentFirstBinding
import kotlinx.coroutines.*


class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private var mDb: AppDatabase? = null
    private lateinit var preference : SharedPreferences

companion object{
    const val LOGINUSER = "login_username"
    const val USERNAME = "username"
    const val CHECKUSER = "check_username"
}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root

    }


    @OptIn(InternalCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDb = AppDatabase.getInstance(requireContext())
         preference = requireContext().getSharedPreferences(LOGINUSER,Context.MODE_PRIVATE)
//        preference = requireContext().getSharedPreferences(SignUpFragment.CHECKUSER,Context.MODE_PRIVATE)




        if (preference.getString(USERNAME,null) != null ){
            findNavController().navigate(R.id.action_loginFragment_to_fragmentMain)
        }

        if ( preference.getString(CHECKUSER,null) != null){
            binding.etUsername.setText( preference.getString(CHECKUSER, "defaultuser"))
        }

        binding.btnSignup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        binding.btnLogin.setOnClickListener {

            if (binding.etUsername.text.toString().isEmpty() || binding.etPassword.text.toString()
                    .isEmpty()
            ) {
                Toast.makeText(requireContext(), "Tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else {
                GlobalScope.async {
                    val login =
                        mDb?.logindao()?.checkLogin(binding.etUsername.text.toString(),binding.etPassword.text.toString())
                    activity?.runOnUiThread {
                        if (login == true)  {
                            val editor: SharedPreferences.Editor = preference.edit()
                            editor.putString(USERNAME, binding.etUsername.text.toString())
                            editor.apply()
                            view.findNavController().navigate(R.id.action_loginFragment_to_fragmentMain)
                            Toast.makeText(requireContext(), "sukses login", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(requireContext(), "gagal login", Toast.LENGTH_SHORT)
                                .show()
                        }

                    }

                }
            }
        }
    }
}