package dao;

import entity.Appointment;
import java.sql.SQLException;
import java.util.List;

public interface IHospitalService {

    Appointment getAppointmentById(int appointmentId) throws SQLException;

    List<Appointment> getAppointmentsForPatient(int patientId);

    List<Appointment> getAppointmentsForDoctor(int doctorId);

    boolean scheduleAppointment(Appointment appointment);

    //boolean updateAppointment(Appointment appointment);

    boolean cancelAppointment(int appointmentId);

    //boolean addPatient(int patientId);

    //boolean addPatient(Patient patientId);
}
