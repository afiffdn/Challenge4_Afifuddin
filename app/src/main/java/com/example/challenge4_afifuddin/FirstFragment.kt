package com.example.challenge4_afifuddin

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.challenge4_afifuddin.database.LoginDatabase
import com.example.challenge4_afifuddin.databinding.FragmentFirstBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


@Suppress("UNREACHABLE_CODE")
class LoginFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private var mDb: LoginDatabase? = null
    private val sharePredfile = "kotlinsharepreference"

    @OptIn(InternalCoroutinesApi::class)
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
        mDb = LoginDatabase.getInstance(requireContext())
        val sharedPreferences =
            requireContext().getSharedPreferences(sharePredfile, Context.MODE_PRIVATE)
        val user = sharedPreferences.getString("registusername", "defaultuser")
        if (user != "defaultuser") {
            binding.etUsername.setText(user)
        }
        val cekLogin = sharedPreferences.getString("key", "defaultkey")
//        if (cekLogin != "defaultkey"){
//            findNavController().navigate(R.id.action_loginFragment_to_fragmentMain)
//        }

        binding.btnSignup.setOnClickListener {
            val moveToSignUp = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
            view.findNavController().navigate(moveToSignUp)
        }

        binding.btnLogin.setOnClickListener {

            if (binding.etUsername.text.toString().isEmpty() || binding.etPassword.text.toString()
                    .isEmpty()
            ) {
                Toast.makeText(requireContext(), "Tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch(Dispatchers.IO) {
                    runBlocking(Dispatchers.Main) {
                        if (user  != "registusername") {
                            val editor: SharedPreferences.Editor = sharedPreferences.edit()
                            editor.putString("username", binding.etUsername.text.toString())
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