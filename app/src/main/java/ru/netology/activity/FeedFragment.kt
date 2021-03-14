package ru.netology.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.dto.Post
import ru.netology.R
import ru.netology.activity.NewPostFragment.Companion.textArg
import ru.netology.adapter.OnListenerPress
import ru.netology.adapter.PostAdapter
import ru.netology.databinding.FragmentFeedBinding
import ru.netology.vievmodel.PostViewModel

class FeedFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val binding = FragmentFeedBinding.inflate(inflater,container, false)


        val adapter = PostAdapter(object : OnListenerPress {
            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }

            override fun onToSendListener(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }
                val shareIntent =
                        Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)
                viewModel.toSendsById(post.id)
            }

            override fun onLikeListener(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onVievingListener(post: Post) {
                viewModel.viewingById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onPlay(post: Post) {
                val url = post.video
                val address: Uri = Uri.parse(url)
                val openlink = Intent(Intent.ACTION_VIEW, address)
                startActivity(openlink)
            }
        })

        binding.cancellation.isGone = true

        binding.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner, { posts ->
            adapter.submitList(posts)
        })

        viewModel.edited.observe(viewLifecycleOwner, { post ->
            if (post.id == 0L) {
                return@observe
            }
            with(binding.content) {
                val text: String = post.content
                val bundle = Bundle().apply {
                    textArg = text
                }
                findNavController().navigate(R.id.action_feedFragment_to_redactorFragment, bundle)

            }
        })

        binding.fab.setOnClickListener {
           findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }
        return binding.root
    }
}


