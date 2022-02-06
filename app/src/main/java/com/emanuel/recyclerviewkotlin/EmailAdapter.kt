package com.emanuel.recyclerviewkotlin

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.graphics.Color
import android.graphics.Typeface.*
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.util.isNotEmpty
import androidx.recyclerview.widget.RecyclerView
import com.emanuel.recyclerviewkotlin.model.Email
import kotlinx.android.synthetic.main.email_item.view.*

class EmailAdapter(val emails: MutableList<Email>) : RecyclerView.Adapter<EmailAdapter.EmailViewHolder>() {

    val selectedItems = SparseBooleanArray()
    private var currentSelectedPos = -1

    inner class EmailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(email: Email) {
            with(email) {
                //Pega um código hash
                val hash = user.hashCode()
                //Pega a primeira letra do usuário e coloca no ícone
                itemView.txt_icon.text = user.first().toString()
                //Cria um novo desenhável para alterar o background e altera a cor de acordo com o código hash
                itemView.txt_icon.background = itemView.oval(Color.rgb(hash, hash / 2, 0))
                itemView.txt_user.text = user
                itemView.txt_subject.text = subject
                itemView.txt_preview.text = preview
                itemView.txt_date.text = date

                //Marca se o email está lido ou não
                itemView.txt_user.setTypeface(DEFAULT, if (unread) BOLD else NORMAL)
                itemView.txt_subject.setTypeface(DEFAULT, if (unread) BOLD else NORMAL)
                itemView.txt_date.setTypeface(DEFAULT, if (unread) BOLD else NORMAL)

                //Marca se o emil é um favorito ou não
                itemView.img_star.setImageResource(
                        if (stared) R.drawable.ic_star_black
                        else R.drawable.ic_star
                )

                if (email.selected) {
                    itemView.txt_icon.background = itemView.txt_icon.oval(
                            Color.rgb(26, 115, 233)
                    )
                    itemView.background = GradientDrawable().apply {
                        shape = GradientDrawable.RECTANGLE
                        cornerRadius = 32f
                        setColor(Color.rgb(232, 240, 253))
                    }
                } else {
                    itemView.background = GradientDrawable().apply {
                        shape = GradientDrawable.RECTANGLE
                        cornerRadius = 32f
                        setColor(Color.WHITE)
                    }
                }

                if (selectedItems.isNotEmpty()) {
                    animate(itemView.txt_icon, email)
                }

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmailViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.email_item, parent, false)
        return EmailViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmailViewHolder, position: Int) {
        holder.bind(emails[position])
        holder.itemView.setOnClickListener {
            if (selectedItems.isNotEmpty())
                onItemClick?.invoke(position)
        }
        holder.itemView.setOnLongClickListener {
            onItemLongClick?.invoke(position)
            return@setOnLongClickListener true
        }

        if(currentSelectedPos == position) currentSelectedPos = -1
    }

    override fun getItemCount() = emails.size

    fun toggleSelection(position: Int) {
        currentSelectedPos = position
        if (selectedItems[position, false]) {
            selectedItems.delete(position)
            emails[position].selected = false
        } else {
            selectedItems.put(position, true)
            emails[position].selected = true
        }

        notifyItemChanged(position)
    }

    fun deleteEmails() {
        emails.removeAll(emails.filter { it.selected })
        notifyDataSetChanged()
        currentSelectedPos = -1
    }

    var onItemClick: ((Int) -> Unit)? = null
    var onItemLongClick: ((Int) -> Unit)? = null
}

private fun View.oval(@ColorInt color: Int): ShapeDrawable {
    val oval = ShapeDrawable(OvalShape())
    with(oval) {
        intrinsicHeight = height
        intrinsicWidth = width
        paint.color = color
    }

    return oval
}

private fun animate(view: TextView, email: Email) {
    val oa1 = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f)
    val oa2 = ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f)

    oa1.interpolator = DecelerateInterpolator()
    oa2.interpolator = AccelerateDecelerateInterpolator()

    oa1.duration = 200
    oa2.duration = 200

    oa1.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
            super.onAnimationEnd(animation)
            if (email.selected)
                view.text = "\u2713"
            oa2.start()
        }
    })
    oa1.start()
}
