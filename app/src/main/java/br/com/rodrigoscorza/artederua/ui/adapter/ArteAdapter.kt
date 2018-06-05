package br.com.rodrigoscorza.artederua.ui.adapter

import android.content.Context
import android.graphics.Bitmap
import android.os.AsyncTask
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import br.com.rodrigoscorza.artederua.R
import br.com.rodrigoscorza.artederua.entities.Arte
import br.com.rodrigoscorza.artederua.util.TypeFace
import com.frosquivel.magicalcamera.Utilities.ConvertSimpleImage

class ArteAdapter(var artes: MutableList<Arte>, context: Context?) : RecyclerView.Adapter<ArteAdapter.ArteViewHolder>() {
    private var cx = context


    interface OnItemClickListener {
        fun OnClick(view: View, position: Int)
    }

    lateinit var mClick: OnItemClickListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArteViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_view, parent, false)
        return ArteViewHolder(v)
    }

    class ArteViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var tvNomeArte: TextView = v.findViewById(R.id.tv_nomeArte)
        var rating: RatingBar = v.findViewById(R.id.rating)
        var iv: ImageView = v.findViewById(R.id.iv_arte)
    }

    override fun getItemCount(): Int {
        return artes.size
    }


    override fun onBindViewHolder(holder: ArteViewHolder, position: Int) {
        val arte = artes[position]
       // holder.tvNomeArte.TypeFace(cx!!)
        holder.rating.rating = arte.nota
        holder.tvNomeArte.text = arte.nome
        FotoAsyncTask(holder.iv).execute(arte.foto)

        holder.itemView.setOnClickListener {
            if (mClick != null) {
                mClick.OnClick(it, position)
            }
        }
    }

    fun setListArtes(artes: MutableList<Arte>) {
        this.artes = artes
    }


    private inner class FotoAsyncTask internal constructor(iv: ImageView) : AsyncTask<String, Void, Bitmap>() {
        val niv = iv

        override fun doInBackground(vararg params: String): Bitmap {
            return ConvertSimpleImage.bytesToBitmap(ConvertSimpleImage.stringBase64ToBytes(params[0]))
        }

        override fun onPostExecute(result: Bitmap?) {
            super.onPostExecute(result)
            niv.setImageBitmap(result)
        }
    }
}