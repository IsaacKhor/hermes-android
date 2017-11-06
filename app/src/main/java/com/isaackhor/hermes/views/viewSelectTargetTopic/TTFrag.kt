package com.isaackhor.hermes.views.viewSelectTargetTopic

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.isaackhor.hermes.R

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [TTFrag.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [TTFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class TTFrag : Fragment() {
  private lateinit var stype: SelectType
  private lateinit var vm: SelectTTViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    arguments?.let {
      stype = SelectType.valueOf(arguments.getString(PARAM_STYPE))
    }

    vm = (context as SelectTTActivity).getViewModel()
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    return inflater?.inflate(R.layout.fragment_topic, container, false)
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
  }

  override fun onDetach() {
    super.onDetach()
  }

  /**
   * This interface must be implemented by activities that contain this
   * fragment to allow an interaction in this fragment to be communicated
   * to the activity and potentially other fragments contained in that
   * activity.
   *
   *
   * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
   */
  enum class SelectType { TARGET, TOPIC }

  companion object {
    val PARAM_STYPE = "param_type"

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TTFrag.
     */
    fun newInstance(op: SelectType): TTFrag {
      val fragment = TTFrag()
      val args = Bundle()
      args.putString(PARAM_STYPE, op.name)
      fragment.arguments = args
      return fragment
    }
  }
}// Required empty public constructor
