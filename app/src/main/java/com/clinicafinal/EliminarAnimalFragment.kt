package com.clinicafinal

import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.loader.app.LoaderManager
import com.clinicafinal.databinding.FragmentEliminarAnimalBinding
import com.google.android.material.snackbar.Snackbar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EliminarAnimalFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EliminarAnimalFragment {
    private var _binding: FragmentEliminarAnimalBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var animal: Animal

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEliminarAnimalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_eliminar

        animal = EliminarAnimalFragment.fromBundle(arguments!!).animal

        binding.textViewNome.text = animal.nome
        binding.textViewTipo.text = animal.tipo
        binding.textViewRaca.text = animal.raca.nome
    }

    fun processaOpcaoMenu(item: MenuItem) : Boolean =
        when(item.itemId) {
            R.id.action_eliminar -> {
                eliminaAnimal()
                true
            }
            R.id.action_cancelar -> {
                true
            }
            else -> false
        }

    private fun eliminaAnimal() {
        val alertDialog = AlertDialog.Builder(requireContext())

        alertDialog.apply {
            setTitle(R.string.eliminar_animal_label)
            setMessage(R.string.confirma_eliminar_animal)
            setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener { dialogInterface, i ->  })
            setPositiveButton(R.string.eliminar, DialogInterface.OnClickListener { dialogInterface, i -> confirmaEliminarAnimal() })
            show()
        }
    }

    private fun confirmaEliminarAnimal() {
        val enderecoAnimal = Uri.withAppendedPath(ContentProviderClinica.ENDERECO_ANIMAL, "${animal.id}")
        val registosEliminados = requireActivity().contentResolver.delete(enderecoAnimal, null, null)

        if (registosEliminados != 1) {
            Snackbar.make(
                binding.textViewTitulo,
                R.string.erro_eliminar_animal,
                Snackbar.LENGTH_INDEFINITE
            ).show()
            return
        }

        Toast.makeText(requireContext(), R.string.animal_eliminado_sucesso, Toast.LENGTH_LONG).show()
        voltaListaClinica()
    }

    private fun voltaListaClinica() {
        val acao = EliminarAnimalFragment.actionEliminarAnimalFragmentToListaClinicaFragment()
        findNavController().navigate(acao)
    }
    }
