package com.swedbank.playground;

import com.google.common.collect.ImmutableList;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Playsite implements IPlaysite {

  private PlaysiteType playsiteType;

  private List<Kid> kidsPlaying = new LinkedList<>();
  private List<Kid> kidsWaiting = new LinkedList<>();
  private int maxKidsPlaying;
  private IVisitRepository visitRepository;

  public Playsite(IVisitRepository visitRepository) {
    this.visitRepository = visitRepository;
  }

  @Override
  public boolean addKid(Kid kid) {
    if (kidsPlaying.size() >= maxKidsPlaying) {
      kidsWaiting.add(kid);
      return false;
    }
    else {
      kidsPlaying.add(kid);
      Visit visit = new Visit();
      visit.setPlaysite(this);
      visit.setKid(kid);
      visit.setStart(true);
      visit.setTime(new Date());
      visitRepository.save(visit);
      return true;
    }
  }

  @Override
  public void removeKid(Kid kid) {
    if (kidsWaiting.remove(kid)) {
      return;
    }

    if (kidsPlaying.remove(kid)) {
      Visit visit = new Visit();
      visit.setPlaysite(this);
      visit.setKid(kid);
      visit.setStart(false);
      visit.setTime(new Date());
      visitRepository.save(visit);

      if (kidsWaiting.size() > 0) {
        Kid kid2 = kidsWaiting.remove(0);
        visit = new Visit();
        visit.setPlaysite(this);
        visit.setKid(kid2);
        visit.setStart(true);
        visit.setTime(new Date());
        visitRepository.save(visit);
        kidsPlaying.add(kid2);
      }
    }
    else {
      throw new RuntimeException("No kid " + kid + " in playsite: " + this);
    }
  }

  @Override
  public List<Kid> kidsPlaying() {
    ImmutableList.Builder<Kid> builder = ImmutableList.builder();
    for (int i = 0; i < kidsPlaying.size(); i++) {
      builder.add(kidsPlaying.get(i));
    }
    return builder.build();
  }

  @Override
  public List<Kid> kidsWaiting() {
    ImmutableList.Builder<Kid> builder = ImmutableList.builder();
    for (int i = 0; i < kidsWaiting.size(); i++) {
      builder.add(kidsWaiting.get(i));
    }
    return builder.build();
  }

  @Override
  public PlaysiteType getPlaysiteType() {
    return playsiteType;
  }

  public void setPlaysiteType(PlaysiteType playsiteType) {
    this.playsiteType = playsiteType;
  }

  public int getMaxKidsPlaying() {
    return maxKidsPlaying;
  }

  public void setMaxKidsPlaying(int maxKidsPlaying) {
    this.maxKidsPlaying = maxKidsPlaying;
  }
}
