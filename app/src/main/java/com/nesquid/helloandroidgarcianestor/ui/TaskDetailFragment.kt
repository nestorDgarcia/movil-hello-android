package com.nesquid.helloandroidgarcianestor.ui

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.nesquid.helloandroidgarcianestor.databinding.FragmentTaskDetailBinding
import com.nesquid.helloandroidgarcianestor.receiver.ReminderReceiver
import com.nesquid.helloandroidgarcianestor.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TaskDetailFragment : Fragment() {

    private var _binding: FragmentTaskDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TaskViewModel by activityViewModels()

    private var selectedTimeInMillis: Long = System.currentTimeMillis()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.selectedTask.observe(viewLifecycleOwner) { task ->
            if (task != null) {
                binding.editTextTitle.setText(task.title)
                binding.editTextDescription.setText(task.description)
                binding.switchReminder.isChecked = task.hasReminder
                selectedTimeInMillis = task.timeInMillis
                updateDateTimeLabel(selectedTimeInMillis)
            } else {
                binding.editTextTitle.text?.clear()
                binding.editTextDescription.text?.clear()
                binding.switchReminder.isChecked = false
                binding.textViewSelectedDateTime.text = "Sin fecha seleccionada"
            }
        }

        binding.buttonPickDateTime.setOnClickListener {
            showDatePicker()
        }

        binding.buttonSave.setOnClickListener {
            val title = binding.editTextTitle.text.toString().trim()
            val desc = binding.editTextDescription.text.toString().trim()
            val hasReminder = binding.switchReminder.isChecked

            if (title.isEmpty()) {
                binding.layoutTitle.error = "El título no puede estar vacío"
                return@setOnClickListener
            }
            binding.layoutTitle.error = null

            viewModel.saveTask(title, desc, selectedTimeInMillis, hasReminder)

            if (hasReminder) {
                scheduleAlarm(title, selectedTimeInMillis)
            }

            findNavController().navigateUp()
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                showTimePicker(calendar)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun showTimePicker(calendar: Calendar) {
        TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                calendar.set(Calendar.SECOND, 0)
                selectedTimeInMillis = calendar.timeInMillis
                updateDateTimeLabel(selectedTimeInMillis)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

    private fun updateDateTimeLabel(timeInMillis: Long): String {
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val formatted = "📅 ${format.format(timeInMillis)}"
        binding.textViewSelectedDateTime.text = formatted
        return formatted
    }

    private fun scheduleAlarm(title: String, timeInMillis: Long) {
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), ReminderReceiver::class.java).apply {
            putExtra("TASK_TITLE", title)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            title.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        try {
            // setExactAndAllowWhileIdle funciona aunque el dispositivo
            // esté en modo ahorro de batería o la app esté cerrada
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                timeInMillis,
                pendingIntent
            )
            val fechaFormateada = updateDateTimeLabel(timeInMillis)
            Toast.makeText(
                requireContext(),
                "✅ Recordatorio programado para $fechaFormateada",
                Toast.LENGTH_SHORT
            ).show()
        } catch (e: SecurityException) {
            Toast.makeText(
                requireContext(),
                "Falta permiso de alarma exacta",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}