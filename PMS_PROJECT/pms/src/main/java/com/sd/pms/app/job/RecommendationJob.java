package com.sd.pms.app.job;

import com.sd.pms.app.dto.EmailDto;
import com.sd.pms.app.entity.Recommendation;
import com.sd.pms.app.repository.RecommendationRepository;
import com.sd.pms.app.service.EmailService;
import com.sd.pms.app.util.FindJobFire;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
@Service
public class RecommendationJob {

	private final RecommendationRepository recommendationRepository;
	private final EmailService emailService;

	    @Scheduled(cron = "0 0 23 * * ?")
//	@Scheduled(fixedDelay = 6000)
	public void fire() {
		//        ArrayList<Recommendation> recommendations = (ArrayList<Recommendation>) recommendationRepository.findAllByNextFireAndActive(LocalDate.now() , Boolean.TRUE);
		ArrayList<Recommendation> recommendations = (ArrayList<Recommendation>) recommendationRepository.findAllByActive(
				Boolean.TRUE);

		recommendations.stream().forEach(e -> {

			EmailDto emailDto = EmailDto.builder().to(e.getPatient().getUser().getEmail()).topic(e.getTopic())
					.body(e.getRecommendation()).build();

			emailService.sendEmail(emailDto);
			createNextFireDate(e);

		});

	}

	private void createNextFireDate(Recommendation recommendation) {

		LocalDate nextFireDay;

		switch (recommendation.getOccurrence()) {
		case DAILY:

			nextFireDay = FindJobFire.nextDay(LocalDate.now());
			recommendation.setLastFire(LocalDate.now());
			recommendation.setNextFire(nextFireDay);
			recommendationRepository.save(recommendation);

			break;

		case WEEKLY:

			nextFireDay = FindJobFire.nextSunday(LocalDate.now());
			recommendation.setLastFire(LocalDate.now());
			recommendation.setNextFire(nextFireDay);
			recommendationRepository.save(recommendation);

			break;

		case MONTHLY:

			nextFireDay = FindJobFire.thisMonthsEnd(LocalDate.now());
			recommendation.setLastFire(LocalDate.now());
			recommendation.setNextFire(nextFireDay);
			recommendationRepository.save(recommendation);

			break;

		case YEARLY:

			nextFireDay = FindJobFire.thisYearsEnd(LocalDate.now());
			recommendation.setLastFire(LocalDate.now());
			recommendation.setNextFire(nextFireDay);
			recommendationRepository.save(recommendation);

			break;

		default:
			log.info("DEFAULT case fired");
			break;
		}

	}

}
