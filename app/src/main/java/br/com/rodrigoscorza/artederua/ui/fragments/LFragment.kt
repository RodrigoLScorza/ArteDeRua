package br.com.rodrigoscorza.artederua.ui.fragments


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.rodrigoscorza.artederua.R
import br.com.rodrigoscorza.artederua.entities.Arte
import br.com.rodrigoscorza.artederua.ui.ArteActivity
import br.com.rodrigoscorza.artederua.ui.PrincipalActivity
import br.com.rodrigoscorza.artederua.ui.adapter.ArteAdapter
import br.com.rodrigoscorza.artederua.ui.adapter.ArteAdapterViewModel
import br.com.rodrigoscorza.artederua.util.Constantes
import br.com.rodrigoscorza.artederua.util.SwipeHandler
import kotlinx.android.synthetic.main.fragment_l.*
import java.io.File
import android.graphics.Bitmap
import com.frosquivel.magicalcamera.Utilities.ConvertSimpleImage
import java.io.FileOutputStream


class LFragment : Fragment(), ArteAdapter.OnItemClickListener {


    private var adapter: ArteAdapter? = null
    private var artes: MutableList<Arte> = listOf<Arte>().toMutableList()
    private lateinit var principalActivity: PrincipalActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        principalActivity = (activity!! as PrincipalActivity)
        return inflater.inflate(R.layout.fragment_l, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        addArte.setOnClickListener {

            if (!principalActivity.enableGPS()) {
                principalActivity.mandaToast(R.string.habiliteLocation)
                return@setOnClickListener
            }

            if (principalActivity.checkpermission()) {
                startActivity(Intent(context, ArteActivity::class.java))
            } else {
                principalActivity.mandaToast(R.string.permissaoLocation)
                principalActivity.permissoesDoApp()
                return@setOnClickListener
            }


        }



        lvArtes.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        adapter = ArteAdapter(artes, context)
        adapter?.mClick = this@LFragment
        lvArtes.adapter = adapter


        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(lvArtes)


    }

    override fun onResume() {
        super.onResume()
        pegaDados()
    }

    private val swipeHandler by lazy {
        object : SwipeHandler(context!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
                if (direction == ItemTouchHelper.RIGHT) {
                    var id = adapter?.artes!!.toMutableList()[viewHolder!!.adapterPosition].id
                    var it = Intent(principalActivity, ArteActivity::class.java)
                    it.putExtra(Constantes.ARTE, id)
                    startActivity(it)
                } else {
                    ArteAdapterViewModel(principalActivity.application).deleteArte(
                            adapter?.artes!!.toMutableList()[viewHolder!!.adapterPosition])
                }
            }
        }
    }


    override fun OnClick(view: View, position: Int) {
        val bitmap = Constantes.converteBitmap(adapter?.artes!!.toMutableList()[position].foto)
        try {
            val file = File(principalActivity.getExternalCacheDir(), adapter?.artes!!.toMutableList()[position].nome)
            val fOut = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut)
            fOut.flush()
            fOut.close()
            file.setReadable(true, false)
            val intent = Intent(android.content.Intent.ACTION_SEND)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
            intent.type = "image/png"
            startActivity(Intent.createChooser(intent, resources.getText(R.string.compartilharImagem)))
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }



    private fun pegaDados() {
        ViewModelProviders.of(this@LFragment)
                .get(ArteAdapterViewModel::class.java)
                .artes
                .observe(this@LFragment, Observer<MutableList<Arte>> {
                    adapter?.setListArtes(it!!)
                    if (it!!.size > 0) {
                        tv_naoExiste.visibility = View.GONE
                    } else {
                        tv_naoExiste.visibility = View.VISIBLE
                    }
                    lvArtes.adapter.notifyDataSetChanged()
                })
    }


}
