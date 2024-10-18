package dao;

import entity.Appointment;
import exception.PatientNumberNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import util.DBConnection;

public class HospitalServiceImpl implements IHospitalService {
    private static Connection connection ;
    
    static {
        connection = DBConnection.getConnection();
    }
    //private static final Connection connection = DBConnection.getConnection();
    private boolean checkPatientExists(int patientId) throws SQLException {
    String sql = "SELECT COUNT(*) FROM patients WHERE patient_id = ?";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, patientId);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next(); // Move to the first (and hopefully only) result
        int count = resultSet.getInt(1);
        return count > 0;
    }
    catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

    @Override
    public Appointment getAppointmentById(int appointmentId) throws SQLException {
        String sql = "SELECT * FROM appointments WHERE appointment_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
           statement.setInt(1, appointmentId);
           ResultSet resultSet = statement.executeQuery();

           if (resultSet.next()) {
            int patientId = resultSet.getInt("patient_id");
            int doctorId = resultSet.getInt("doctor_id");
            LocalDate appointmentDate = resultSet.getDate("appointment_date").toLocalDate();
            String description = resultSet.getString("description");
            return new Appointment(appointmentId, patientId, doctorId, appointmentDate, description);
            } else {
               return null; // No appointment found with the given ID
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
    }
}
    @Override
    public boolean scheduleAppointment(Appointment appointment) {
        

        String sql = "INSERT INTO appointments (appointment_id , patient_id, doctor_id, appointment_date, description) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, appointment.getAppointmentId());
            statement.setInt(2, appointment.getPatientId());
            statement.setInt(3, appointment.getDoctorId());
            statement.setDate(4, java.sql.Date.valueOf(appointment.getAppointmentDate()));
            statement.setString(5, appointment.getDescription());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Appointment> getAppointmentsForPatient(int patientId) {
        

        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE patient_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            if (!checkPatientExists(patientId)) {
                throw new PatientNumberNotFoundException("Patient with ID " + patientId + " not found.");
            }

            statement.setInt(1, patientId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int appointmentId = resultSet.getInt("appointment_id");
                int doctorId = resultSet.getInt("doctor_id");
                LocalDate appointmentDate = resultSet.getDate("appointment_date").toLocalDate();
                String description = resultSet.getString("description");
                appointments.add(new Appointment(appointmentId, patientId, doctorId, appointmentDate, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    @Override
    public List<Appointment> getAppointmentsForDoctor(int doctorId) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE doctor_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, doctorId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int appointmentId = resultSet.getInt("appointment_id");
                int patientId = resultSet.getInt("patient_id");
                LocalDate appointmentDate = resultSet.getDate("appointment_date").toLocalDate();
                String description = resultSet.getString("description");
                appointments.add(new Appointment(appointmentId, patientId, doctorId, appointmentDate, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    @Override
    public boolean cancelAppointment(int appointmentId) {
        String sql = "DELETE FROM appointments WHERE appointment_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, appointmentId);
            return statement.executeUpdate() > 0;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}