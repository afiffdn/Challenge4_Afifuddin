package com.example.challenge4_afifuddin

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.challenge4_afifuddin.database.LoginDatabase
import com.example.challenge4_afifuddin.databinding.FragmentMainBinding


class FragmentMain : Fragment() {
    private var _binding : FragmentMainBinding? = null
    private val binding get() = _binding!!
    private var mDb: LoginDatabase? = null
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

    @SuppressLint("SetTextI18n", "CommitPrefEdits")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDb = LoginDatabase.getInstance(requireContext())
        val sharedPreferences =
            requireContext().getSharedPreferences(sharePredfile, Context.MODE_PRIVATE)

        val name = sharedPreferences.getString("key","apip")
        binding.tvHello.setText("Hello,${name}")
        val editor :SharedPreferences.Editor = sharedPreferences.edit()
        binding.logout.setOnClickListener {
            editor.clear()
            editor.apply()
            findNavController().navigate(R.id.action_fragmentMain_to_loginFragment)
        }
    }

}