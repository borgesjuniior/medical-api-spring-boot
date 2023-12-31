package med.vol.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import med.vol.api.entities.Doctor;

public interface IDoctorRepository extends JpaRepository<Doctor, Long> {

}
