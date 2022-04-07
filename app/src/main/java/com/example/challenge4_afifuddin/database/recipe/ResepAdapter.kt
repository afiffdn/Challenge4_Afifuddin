package com.example.challenge4_afifuddin.database.recipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.challenge4_afifuddin.databinding.FragmentListMenuBinding

class ResepAdapter(
    private val listResep : List<Resep>,
    private val onEditClickListener:(Resep)->Unit,
    private val onDeleteClickListener:(Resep)->Unit,
) : RecyclerView.Adapter<ResepAdapter.ViewHolder>()
{
    class ViewHolder(val binding: FragmentListMenuBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentListMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position:Int){
    holder.binding.namaMakanan.text = listResep[position].name
    holder.binding.isiIngredient.text = listResep[position].ingredient
    holder.binding.isiHowTo.text = listResep[position].step
    holder.binding.ivEdit.setOnClickListener {
        onEditClickListener.invoke(listResep[position])
    }
        holder.binding.ivRemove.setOnClickListener{
            onDeleteClickListener.invoke(listResep[position])
        }
//        val imageStream = ByteArrayInputStream(listResep?.get(position)!!.image)
//        val theImage = BitmapFactory.decodeStream(imageStream)
//        holder.binding.gambarMakanan.setImageBitmap(theImage)
    }

    override fun getItemCount(): Int =  listResep.size

}