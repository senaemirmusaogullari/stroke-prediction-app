package com.example.health_risk.model;

import jakarta.persistence.*;

@Entity
@Table(name = "health_risks")
public class HealthRisk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int gender;
    private double age;
    private int hypertension;
    private int heartDisease;
    private int everMarried;
    private int workType;
    private int residenceType;
    
    private int workTypeGovtJob;
    private int workTypeNeverWorked;
    private int workTypePrivate;
    private int workTypeSelfEmployed;
    private int workTypeChildren;

    private int smokingStatusFormerlySmoked;
    private int smokingStatusNeverSmoked;
    private int smokingStatusSmokes;
    private int smokingStatusUnknown;

    private double avgGlucoseLevel;
    private double bmi;
    private int stroke;

    // Getter ve Setter metodları

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public int getHypertension() {
        return hypertension;
    }

    public void setHypertension(int hypertension) {
        this.hypertension = hypertension;
    }

    public int getHeartDisease() {
        return heartDisease;
    }

    public void setHeartDisease(int heartDisease) {
        this.heartDisease = heartDisease;
    }

    public int getEverMarried() {
        return everMarried;
    }

    public void setEverMarried(int everMarried) {
        this.everMarried = everMarried;
    }

    public int getWorkType() {
        return workType;
    }

    public void setWorkType(int workType) {
        this.workType = workType;
    }

    public int getResidenceType() {
        return residenceType;
    }

    public void setResidenceType(int residenceType) {
        this.residenceType = residenceType;
    }

    public double getAvgGlucoseLevel() {
        return avgGlucoseLevel;
    }

    public void setAvgGlucoseLevel(double avgGlucoseLevel) {
        this.avgGlucoseLevel = avgGlucoseLevel;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public int getStroke() {
        return stroke;
    }

    public void setStroke(int stroke) {
        this.stroke = stroke;
    }

	public int getWorkTypeGovtJob() {
		return workTypeGovtJob;
	}

	public void setWorkTypeGovtJob(int workTypeGovtJob) {
		this.workTypeGovtJob = workTypeGovtJob;
	}

	public int getWorkTypeNeverWorked() {
		return workTypeNeverWorked;
	}

	public void setWorkTypeNeverWorked(int workTypeNeverWorked) {
		this.workTypeNeverWorked = workTypeNeverWorked;
	}

	public int getWorkTypePrivate() {
		return workTypePrivate;
	}

	public void setWorkTypePrivate(int workTypePrivate) {
		this.workTypePrivate = workTypePrivate;
	}

	public int getWorkTypeSelfEmployed() {
		return workTypeSelfEmployed;
	}

	public void setWorkTypeSelfEmployed(int workTypeSelfEmployed) {
		this.workTypeSelfEmployed = workTypeSelfEmployed;
	}

	public int getWorkTypeChildren() {
		return workTypeChildren;
	}

	public void setWorkTypeChildren(int workTypeChildren) {
		this.workTypeChildren = workTypeChildren;
	}

	public int getSmokingStatusFormerlySmoked() {
		return smokingStatusFormerlySmoked;
	}

	public void setSmokingStatusFormerlySmoked(int smokingStatusFormerlySmoked) {
		this.smokingStatusFormerlySmoked = smokingStatusFormerlySmoked;
	}

	public int getSmokingStatusNeverSmoked() {
		return smokingStatusNeverSmoked;
	}

	public void setSmokingStatusNeverSmoked(int smokingStatusNeverSmoked) {
		this.smokingStatusNeverSmoked = smokingStatusNeverSmoked;
	}

	public int getSmokingStatusSmokes() {
		return smokingStatusSmokes;
	}

	public void setSmokingStatusSmokes(int smokingStatusSmokes) {
		this.smokingStatusSmokes = smokingStatusSmokes;
	}

	public int getSmokingStatusUnknown() {
		return smokingStatusUnknown;
	}

	public void setSmokingStatusUnknown(int smokingStatusUnknown) {
		this.smokingStatusUnknown = smokingStatusUnknown;
	}
}