package ru.netology.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_redactor.*
import ru.netology.R
import ru.netology.activity.NewPostFragment.Companion.textArg
import ru.netology.databinding.FragmentNewPostBinding

import ru.netology.databinding.FragmentRedactorBinding
import ru.netology.utils.AndroidUtils
import ru.netology.vievmodel.PostViewModel

class RedactorFragment : Fragment() {
    private val viewModel: PostViewModel by viewModels(
            ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRedactorBinding.inflate(inflater, container, false)

        arguments?.textArg
                ?.let(binding.edit::setText)

        binding.ok.setOnClickListener {
            viewModel.changeContent(binding.edit.text.toString())
            viewModel.save()
            AndroidUtils.hideKeyboard(requireView())
            findNavController().navigateUp()
        }

        arguments?.textArg
                ?.let (binding.edit::setText)

        return binding.root
    }


}
