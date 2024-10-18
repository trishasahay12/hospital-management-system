package main;

import dao.HospitalServiceImpl;
import dao.IHospitalService;
import entity.Appointment;
import exception.PatientNumberNotFoundException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MainModule {

    private static final IHospitalService hospitalService = new HospitalServiceImpl();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {

        int choice = 0;

        do {
            displayMenu();
            try {
                choice = scanner.nextInt();
                handleMenuChoice(choice);
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 6.");
                scanner.nextLine(); // Clear the scanner buffer
            } catch (PatientNumberNotFoundException e) {
                System.out.println(e.getMessage());
            }
        } while (choice!= 6);

        System.out.println("Exiting Hospital Management System.");
    }

    private static void displayMenu() {
        System.out.println("\nHospital Management System");
        System.out.println("1. Get Appointment by ID");
        System.out.println("2. Schedule Appointment");
        System.out.println("3. Get Appointments for Patient");
        System.out.println("4. Get Appointments for Doctor");
        System.out.println("5. Cancel Appointment");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void handleMenuChoice(int choice) throws PatientNumberNotFoundException, SQLException {
        switch (choice) {
            case 1:
                getAppointmentById();
                break;
            case 2:
                scheduleAppointment();
                break;
            case 3:
                getAppointmentsForPatient();
                break;
            case 4:
                getAppointmentsForDoctor();
                break;
            case 5:
                cancelAppointment();
                break;
            case 6:
                // Exit handled in main loop
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void getAppointmentById() throws PatientNumberNotFoundException, SQLException {
        System.out.print("Enter appointment ID: ");
        int appointmentId = scanner.nextInt();

        Appointment appointment = hospitalService.getAppointmentById(appointmentId);
        if (appointment != null) {
            System.out.println("Appointment Details:");
            System.out.println(appointment);
        } else {
            System.out.println("No appointment found with ID " + appointmentId);
        }
    }


    private static void scheduleAppointment() throws PatientNumberNotFoundException {
        System.out.print("Enter appointment ID: ");
        int appointmentId = scanner.nextInt();
        System.out.print("Enter patient ID: ");
        int patientId = scanner.nextInt();
        System.out.print("Enter doctor ID: ");
        int doctorId = scanner.nextInt();
        System.out.print("Enter appointment date (YYYY-MM-DD): ");
        LocalDate appointmentDate = LocalDate.parse(scanner.next());
        System.out.print("Enter appointment description: ");
        scanner.nextLine(); // Clear newline character from previous input
        String description = scanner.nextLine();

        Appointment appointment = new Appointment(appointmentId, patientId, doctorId, appointmentDate, description);
        // Call service layer to schedule appointment
        // (implementation depends on HospitalServiceImpl)
        boolean scheduled = hospitalService.scheduleAppointment(appointment);
        if (scheduled) {
            System.out.println("Appointment scheduled successfully!");
        } else {
            System.out.println("Failed to schedule appointment.");
        }
    }

    private static void getAppointmentsForPatient() throws PatientNumberNotFoundException {
        System.out.print("Enter patient ID: ");
        int patientId = scanner.nextInt();

        List<Appointment> appointments = hospitalService.getAppointmentsForPatient(patientId);
        if (appointments.isEmpty()) {
            System.out.println("No appointments found for patient ID " + patientId);
        } else {
            System.out.println("Appointments for patient ID " + patientId + ":");
            for (Appointment appointment : appointments) {
                System.out.println(appointment);
            }
        }
    }

    private static void getAppointmentsForDoctor() throws PatientNumberNotFoundException {
        System.out.print("Enter doctor ID: ");
        int doctorId = scanner.nextInt();

        List<Appointment> appointments = hospitalService.getAppointmentsForDoctor(doctorId);
        if (appointments.isEmpty()) {
            System.out.println("No appointments found for doctor ID " + doctorId);
        } else {
            System.out.println("Appointments for doctor ID " + doctorId + ":");
            for (Appointment appointment : appointments) {
                System.out.println(appointment);
            }
        }
    }

    private static void cancelAppointment() throws PatientNumberNotFoundException {
        System.out.print("Enter appointment ID: ");
        int appointmentId = scanner.nextInt();

        boolean canceled = hospitalService.cancelAppointment(appointmentId);
        if (canceled) {
            System.out.println("Appointment canceled successfully!");
        } else {
            System.out.println("Failed to cancel appointment.");
        }
    }
}