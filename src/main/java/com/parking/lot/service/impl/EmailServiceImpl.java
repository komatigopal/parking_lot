package com.parking.lot.service.impl;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parking.lot.dto.EmailDetails;
import com.parking.lot.entity.ParkingSlot;
import com.parking.lot.entity.ParkingVehicle;
import com.parking.lot.entity.Vehicle;
import com.parking.lot.service.ContactService;
import com.parking.lot.service.EmailService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String sender;

	@Autowired
	ContactService contactService;

	@Override
	public String sendSimpleMail(EmailDetails details) {
		try {
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setFrom(sender);
			mailMessage.setTo(details.getRecipient());
			mailMessage.setText(details.getMsgBody());
			mailMessage.setSubject(details.getSubject());
			javaMailSender.send(mailMessage);
			return "Mail Sent Successfully...";
		} catch (Exception e) {
			return "Error while Sending Mail " + e;
		}
	}

	@Override
	public String sendMailWithAttachment(EmailDetails details) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;
		try {
			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setFrom(sender);
			mimeMessageHelper.setTo(details.getRecipient());
			mimeMessageHelper.setText(details.getMsgBody());
			mimeMessageHelper.setSubject(details.getSubject());
			FileSystemResource file = new FileSystemResource(new File(details.getAttachment()));
			mimeMessageHelper.addAttachment(file.getFilename(), file);
			javaMailSender.send(mimeMessage);
			return "Mail sent Successfully";
		} catch (MessagingException e) {
			return "Error while sending mail!!! " + e;
		}
	}

	@Override
	public String sendSimpleMail(ParkingVehicle parkingVehicle) {
		String subject = "Dear " + parkingVehicle.getVehicle().getUser().getName() + ", "
				+ parkingVehicle.getVehicle().getId() + " is Parked at Parking Slot - "
				+ parkingVehicle.getParkingSlot().getId() + ", You can "
				+ parkingVehicle.getParkingSlot().getLocation().getNavigation() + " Please find the Parking details.";
		if (!parkingVehicle.isStatus()) {
			subject = "Dear " + parkingVehicle.getVehicle().getUser().getName() + ", "
					+ parkingVehicle.getVehicle().getId() + " Realsed from Parking Slot - "
					+ parkingVehicle.getParkingSlot().getId() + ", You can "
					+ parkingVehicle.getParkingSlot().getLocation().getNavigation()
					+ " Please find the Parking details.";
		}
		String path = "/app/json/parkingVehicle.json";
		filePreparation(path);
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(new File(path), parkingVehicle);
		} catch (Exception e) {
			e.printStackTrace();
		}
		EmailDetails emailDetails = EmailDetails.builder()
				.recipient(parkingVehicle.getVehicle().getUser().getContact().getEmail()).msgBody(subject)
				.subject(subject).attachment(path).build();
		log.info("emailDetails - " + emailDetails);
		return this.sendMailWithAttachment(emailDetails);
	}

	private void filePreparation(String path) {
		try {
			File file = new File(path);
			if (!file.exists()) {
				file.getParentFile().mkdirs();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String sendSimpleMail(ParkingSlot parkingSlot) {
		String subject = "Dear admin, " + parkingSlot.getId() + " is Created / Updated at Parking Slot - "
				+ parkingSlot.getLocation().getNavigation() + " Please find the Parking Slot details.";
		if (!parkingSlot.isParkingStatus() && !parkingSlot.isActivityStatus()) {
			subject = "Dear admin, " + parkingSlot.getId() + " is Closed at Parking Slot - "
					+ parkingSlot.getLocation().getNavigation() + " Please find the Parking Slot  details.";
		}
		String path = "/app/json/parkingSlot.json";
		filePreparation(path);
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(new File(path), parkingSlot);
		} catch (Exception e) {
			e.printStackTrace();
		}
		EmailDetails emailDetails = EmailDetails.builder().recipient(contactService.findById(0l).get().getEmail())
				.msgBody(subject).subject(subject).attachment(path).build();
		log.info("emailDetails - " + emailDetails);
		return this.sendMailWithAttachment(emailDetails);
	}

	@Override
	public String sendSimpleMail(Vehicle vehicle) {
		String subject = vehicle.getId() + " is Added at Parking Slot - " + vehicle.getId()
				+ " Please find the Parking details.";
		String path = "/app/json/vehicle.json";
		filePreparation(path);
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(new File(path), vehicle);
		} catch (Exception e) {
			e.printStackTrace();
		}
		EmailDetails emailDetails = EmailDetails.builder().recipient(vehicle.getUser().getContact().getEmail())
				.msgBody(subject).subject(subject).attachment(path).build();
		log.info("emailDetails - " + emailDetails);
		return this.sendMailWithAttachment(emailDetails);
	}
}