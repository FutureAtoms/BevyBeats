package com.futureatoms.bevybeats.ui.fragment.ai

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.futureatoms.bevybeats.R
import com.futureatoms.bevybeats.databinding.FragmentAiBinding
import kotlin.random.Random

class AiFragment : Fragment() {

    private var _binding: FragmentAiBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up button click listeners for main AI features
        binding.btnCreatePlaylist.setOnClickListener {
            Toast.makeText(requireContext(), getString(R.string.ai_not_available), Toast.LENGTH_LONG).show()
        }

        binding.btnCreateMusic.setOnClickListener {
            Toast.makeText(requireContext(), getString(R.string.ai_not_available), Toast.LENGTH_LONG).show()
        }
        
        binding.btnTalkMusic.setOnClickListener {
            Toast.makeText(requireContext(), getString(R.string.ai_not_available), Toast.LENGTH_LONG).show()
        }
        
        binding.btnMusicPrediction.setOnClickListener {
            Toast.makeText(requireContext(), getString(R.string.ai_not_available), Toast.LENGTH_LONG).show()
        }

        // Add some animation to the BevyBot greeting
        animateGreeting()
        
        // Animate the robot image with a subtle floating animation
        animateRobot()

        // Animate the particles
        animateParticles()
        animateAdditionalParticles()
    }
    
    private fun animateGreeting() {
        val scaleX = ObjectAnimator.ofFloat(binding.bevybotGreeting, "scaleX", 1f, 1.1f, 1f)
        val scaleY = ObjectAnimator.ofFloat(binding.bevybotGreeting, "scaleY", 1f, 1.1f, 1f)
        
        scaleX.duration = 2000
        scaleY.duration = 2000
        
        scaleX.repeatCount = ValueAnimator.INFINITE
        scaleY.repeatCount = ValueAnimator.INFINITE
        
        scaleX.repeatMode = ValueAnimator.REVERSE
        scaleY.repeatMode = ValueAnimator.REVERSE
        
        scaleX.interpolator = AccelerateDecelerateInterpolator()
        scaleY.interpolator = AccelerateDecelerateInterpolator()
        
        scaleX.start()
        scaleY.start()
    }

    private fun animateRobot() {
        // Enhanced robot animation - more playful floating
        val translateY = ObjectAnimator.ofFloat(binding.imageView, "translationY", 0f, -20f, 0f)
        translateY.duration = 2500
        translateY.repeatCount = ValueAnimator.INFINITE
        translateY.repeatMode = ValueAnimator.REVERSE
        translateY.interpolator = AccelerateDecelerateInterpolator()
        translateY.start()
        
        // Slightly more pronounced rotation for personality
        val rotation = ObjectAnimator.ofFloat(binding.imageView, "rotation", -5f, 5f)
        rotation.duration = 3000
        rotation.repeatCount = ValueAnimator.INFINITE
        rotation.repeatMode = ValueAnimator.REVERSE
        rotation.interpolator = AccelerateDecelerateInterpolator()
        rotation.start()
        
        // Add a little scaling animation for "breathing" effect
        val scaleX = ObjectAnimator.ofFloat(binding.imageView, "scaleX", 1f, 1.05f, 1f)
        val scaleY = ObjectAnimator.ofFloat(binding.imageView, "scaleY", 1f, 1.05f, 1f)
        
        scaleX.duration = 3500
        scaleY.duration = 3500
        
        scaleX.repeatCount = ValueAnimator.INFINITE
        scaleY.repeatCount = ValueAnimator.INFINITE
        
        scaleX.start()
        scaleY.start()
    }

    private fun animateParticles() {
        // Just animate the first particle since we've simplified the layout
        val particle = binding.particle1
        
        // Random duration between 3-8 seconds for varied motion
        val duration = Random.nextInt(3000, 8000).toLong()
        
        // Create move animation (Y-axis) with larger range
        val moveY = PropertyValuesHolder.ofFloat(
            "translationY", 
            0f, 
            Random.nextInt(-70, 70).toFloat()
        )
        
        // Create move animation (X-axis) with larger range
        val moveX = PropertyValuesHolder.ofFloat(
            "translationX", 
            0f, 
            Random.nextInt(-70, 70).toFloat()
        )
        
        // Create alpha animation (fade in/out) with variance
        val alpha = PropertyValuesHolder.ofFloat(
            "alpha",
            Random.nextFloat() * 0.2f + 0.1f,  // Start between 0.1 and 0.3
            Random.nextFloat() * 0.5f + 0.3f   // End between 0.3 and 0.8
        )
        
        // Create scale animation with variance
        val scaleRandom = Random.nextFloat() * 0.5f + 0.7f  // Between 0.7 and 1.2
        val scale = PropertyValuesHolder.ofFloat(
            "scaleX", 
            0.7f, 
            scaleRandom
        )
        val scaleY = PropertyValuesHolder.ofFloat(
            "scaleY", 
            0.7f, 
            scaleRandom
        )
        
        // Add rotation for more magical feel
        val rotation = PropertyValuesHolder.ofFloat(
            "rotation",
            0f,
            Random.nextFloat() * 360f  // Full rotation
        )
        
        // Combine all animations
        val animator = ObjectAnimator.ofPropertyValuesHolder(
            particle,
            moveX,
            moveY,
            alpha,
            scale,
            scaleY,
            rotation
        )
        
        // Random start delay for more natural appearance
        animator.startDelay = Random.nextLong(0, 1500)
        animator.duration = duration
        animator.repeatCount = ValueAnimator.INFINITE
        animator.repeatMode = ValueAnimator.REVERSE
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.start()
    }
    
    private fun animateAdditionalParticles() {
        // Animate the other particles with different timing
        animateParticleView(binding.particle2, Random.nextInt(2000, 6000).toLong())
        animateParticleView(binding.particle3, Random.nextInt(4000, 7000).toLong())
    }
    
    private fun animateParticleView(particleView: View, duration: Long) {
        // Create move animation (Y-axis)
        val moveY = PropertyValuesHolder.ofFloat(
            "translationY", 
            0f, 
            Random.nextInt(-50, 50).toFloat()
        )
        
        // Create move animation (X-axis)
        val moveX = PropertyValuesHolder.ofFloat(
            "translationX", 
            0f, 
            Random.nextInt(-50, 50).toFloat()
        )
        
        // Create alpha animation
        val alpha = PropertyValuesHolder.ofFloat(
            "alpha",
            0.2f,
            0.7f
        )
        
        // Create scale animation
        val scale = PropertyValuesHolder.ofFloat("scaleX", 0.8f, 1.2f)
        val scaleY = PropertyValuesHolder.ofFloat("scaleY", 0.8f, 1.2f)
        
        // Combine all animations
        val animator = ObjectAnimator.ofPropertyValuesHolder(
            particleView,
            moveX,
            moveY,
            alpha,
            scale,
            scaleY
        )
        
        animator.startDelay = Random.nextLong(0, 1000)
        animator.duration = duration
        animator.repeatCount = ValueAnimator.INFINITE
        animator.repeatMode = ValueAnimator.REVERSE
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 