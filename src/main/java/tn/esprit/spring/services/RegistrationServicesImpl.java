package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IRegistrationRepository;
import tn.esprit.spring.repositories.ISkierRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
@Slf4j
@AllArgsConstructor
@Service
public class RegistrationServicesImpl implements  IRegistrationServices{

    private IRegistrationRepository registrationRepository;
    private ISkierRepository skierRepository;
    private ICourseRepository courseRepository;


    @Override
    public Registration addRegistrationAndAssignToSkier(Registration registration, Long numSkier) {
        Skier skier = skierRepository.findById(numSkier).orElse(null);
        registration.setSkier(skier);
        return registrationRepository.save(registration);
    }

    @Override
    public Registration assignRegistrationToCourse(Long numRegistration, Long numCourse) {
        Registration registration = registrationRepository.findById(numRegistration).orElse(null);
        Course course = courseRepository.findById(numCourse).orElse(null);
        registration.setCourse(course);
        return registrationRepository.save(registration);
    }

    @Transactional
    @Override
    public Registration addRegistrationAndAssignToSkierAndCourse(Registration registration, Long numSkieur, Long numCours) {
        Skier skier = skierRepository.findById(numSkieur).orElse(null);
        Course course = courseRepository.findById(numCours).orElse(null);

        if (skier == null || course == null) {
            return null;
        }

        if (isAlreadyRegistered(registration.getNumWeek(), skier.getNumSkier(), course.getNumCourse())) {
            return null;
        }

        int ageSkieur = calculateAge(skier.getDateOfBirth());
        log.info("Age " + ageSkieur);

        if (isIndividualCourse(course) || (isCollectiveChildrenCourse(course) && ageSkieur < 16) || (isCollectiveAdultCourse(course) && ageSkieur >= 16)) {
            return processCourseRegistration(registration, skier, course);
        } else {
            log.info("Sorry, your age doesn't allow you to register for this course!");
            log.info("Try to Register for a " + (course.getTypeCourse() == TypeCourse.COLLECTIVE_CHILDREN ? "Collective Adult" : "Collective Child") + " Course...");
            return null;
        }
    }

    private boolean isAlreadyRegistered(int numWeek, long skierNum, long courseNum) {
        return registrationRepository.countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(numWeek, skierNum, courseNum) >= 1;
    }

    private int calculateAge(LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    private boolean isIndividualCourse(Course course) {
        return course.getTypeCourse() == TypeCourse.INDIVIDUAL;
    }

    private boolean isCollectiveChildrenCourse(Course course) {
        return course.getTypeCourse() == TypeCourse.COLLECTIVE_CHILDREN;
    }

    private boolean isCollectiveAdultCourse(Course course) {
        return !isIndividualCourse(course) && !isCollectiveChildrenCourse(course);
    }

    private Registration processCourseRegistration(Registration registration, Skier skier, Course course) {
        if (registrationRepository.countByCourseAndNumWeek(course, registration.getNumWeek()) < 6) {
            log.info("Course successfully added !");
            return assignRegistration(registration, skier, course);
        } else {
            log.info("Full Course ! Please choose another week to register !");
            return null;
        }
    }

    private Registration assignRegistration(Registration registration, Skier skier, Course course) {
        registration.setSkier(skier);
        registration.setCourse(course);
        return registrationRepository.save(registration);
    }

    @Override
    public List<Integer> numWeeksCourseOfInstructorBySupport(Long numInstructor, Support support) {
        return registrationRepository.numWeeksCourseOfInstructorBySupport(numInstructor, support);
    }

}
