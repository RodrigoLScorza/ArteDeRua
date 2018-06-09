package br.com.rodrigoscorza.artederua.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import br.com.rodrigoscorza.artederua.R
import kotlinx.android.synthetic.main.dialog_info.*
import android.content.Intent
import android.net.Uri
import br.com.rodrigoscorza.artederua.ui.viewmodel.LoginOuCadastroActivityViewModel


class DialogInfo(context: Context, viewModel: LoginOuCadastroActivityViewModel) : Dialog(context) {
    var cx = context
    var vm = viewModel


    init {
        this.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val view = View.inflate(this.context, R.layout.dialog_info, null)
        this.setContentView(view)
    }


    override fun show() {
        super.show()
        vm.dialogInfo = true


        tvTel.setOnClickListener {
            val phone = "11947200516"
            val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
            cx.startActivity(intent)
        }
    }

    override fun dismiss() {
        super.dismiss()
        vm.dialogInfo = false
    }

}