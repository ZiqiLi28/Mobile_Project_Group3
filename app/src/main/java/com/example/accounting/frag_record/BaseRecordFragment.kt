package com.example.accounting.frag_record

import android.annotation.SuppressLint
import android.inputmethodservice.KeyboardView
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.accounting.R
import com.example.accounting.db.AccountBean
import com.example.accounting.db.TypeBean
import com.example.accounting.utils.NoteDialog
import com.example.accounting.utils.KeyBoardUtils
import com.example.accounting.utils.SelectTimeDialog
import java.text.SimpleDateFormat
import java.util.*

abstract class BaseRecordFragment : Fragment(), View.OnClickListener {

    private lateinit var keyboardView: KeyboardView
    lateinit var moneyEt: EditText
    lateinit var typeIv: ImageView
    lateinit var typeTv: TextView
    lateinit var noteTv: TextView
    lateinit var timeTv: TextView
    private lateinit var typeGv: GridView
    var typeList = ArrayList<TypeBean>()
    lateinit var adapter: TypeBaseAdapter

    var accountBean = AccountBean()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountBean.typename = "Other"
        accountBean.sImageId = R.mipmap.ic_qita_fs
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_expense, container, false)
        initView(view)
        serInitTime()
        loadDataGV()
        seGVListener()
        return view
    }

    @SuppressLint("SimpleDateFormat")
    private fun serInitTime() {
        val date = Date()
        val sdf = SimpleDateFormat("dd MM yyyy HH:mm")
        val time = sdf.format(date)
        timeTv.text = time
        accountBean.time = time

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        accountBean.year = year
        accountBean.month = month
        accountBean.day = day
    }

    private fun seGVListener() {
        typeGv.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            adapter.selectPos = position
            adapter.notifyDataSetInvalidated()
            val typeBean = typeList[position]
            val typename = typeBean.typename
            typeTv.text = typename
            accountBean.typename = typename

            val simageId = typeBean.simageId
            typeIv.setImageResource(simageId)
            accountBean.sImageId = simageId
        }
    }

    open fun loadDataGV() {
        adapter = TypeBaseAdapter(requireContext(), typeList)
        typeGv.adapter = adapter
    }

    private fun initView(view: View) {
        keyboardView = view.findViewById(R.id.frag_record_keyboard)
        moneyEt = view.findViewById(R.id.frag_record_et_money)
        typeIv = view.findViewById(R.id.frag_record_iv)
        typeGv = view.findViewById(R.id.frag_record_gv)
        typeTv = view.findViewById(R.id.frag_record_tv_type)
        noteTv = view.findViewById(R.id.frag_record_tv_note)
        timeTv = view.findViewById(R.id.frag_record_tv_time)

        noteTv.setOnClickListener(this)
        timeTv.setOnClickListener(this)

        val boardUtils = KeyBoardUtils(keyboardView, moneyEt)
        boardUtils.showKeyboard()
        boardUtils.setOnEnsureListenerCustom(object : KeyBoardUtils.OnEnsureListener {
            override fun onEnsure() {
                val moneyStr = moneyEt.text.toString()

                if (TextUtils.isEmpty(moneyStr) || moneyStr == "0") {
                    requireActivity().finish()
                    return
                }
                val money = moneyStr.toFloat()
                accountBean.money = money

                saveAccountToDB()
                requireActivity().finish()
            }
        })
    }

    abstract fun saveAccountToDB()

    override fun onClick(v: View) {
        when (v.id) {
            R.id.frag_record_tv_time -> showTimeDialog()
            R.id.frag_record_tv_note -> showBZDialog()
        }
    }


    private fun showTimeDialog() {
        val dialog = SelectTimeDialog(requireContext())
        dialog.show()
        dialog.setOnEnsureListener(object : SelectTimeDialog.OnEnsureListener {
            override fun onEnsure(time: String, year: Int, month: Int, day: Int) {
                timeTv.text = time
                accountBean.time = time
                accountBean.year = year
                accountBean.month = month
                accountBean.day = day
            }
        })
    }

    private fun showBZDialog() {
        val dialog = context?.let { NoteDialog(it, "") }
        dialog?.show()
        dialog?.setDialogSize()
        dialog?.setOnEnsureListener(object : NoteDialog.OnEnsureListener {
            override fun onEnsure() {
                val msg = dialog.getEditText()
                if (!TextUtils.isEmpty(msg)) {
                    noteTv.text = msg
                    accountBean.note = msg
                }
                dialog.cancel()
            }
        })
    }
}
