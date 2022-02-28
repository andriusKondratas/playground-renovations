package com.swedbank.playground;

import com.google.common.collect.ImmutableList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaySite implements IPlaySite {

  final List<Kid> kidsPlaying = new LinkedList<>();
  final List<Kid> kidsWaiting = new LinkedList<>();
  final IVisitRepository visitRepository;

  int maxKidsPlaying;

  public PlaySite(IVisitRepository visitRepository) {
    this.visitRepository = visitRepository;
  }

  @Override
  public boolean addKid(Kid kid) {
    if (kidsPlaying.size() >= maxKidsPlaying) {
      kidsWaiting.add(kid);
      return false;
    } else {
      kidsPlaying.add(kid);
      Visit visit = new Visit();
      visit.setPlaySite(this);
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
      var visit = new Visit();
      visit.setPlaySite(this);
      visit.setKid(kid);
      visit.setStart(false);
      visit.setTime(new Date());
      visitRepository.save(visit);

      if (kidsWaiting.size() > 0) {
        var kid2 = kidsWaiting.remove(0);
        visit = new Visit();
        visit.setPlaySite(this);
        visit.setKid(kid2);
        visit.setStart(true);
        visit.setTime(new Date());
        visitRepository.save(visit);
        kidsPlaying.add(kid2);
      }
    } else {
      throw new RuntimeException("No kid " + kid + " in playSite: " + this);
    }
  }

  @Override
  public List<Kid> kidsPlaying() {
    ImmutableList.Builder<Kid> builder = ImmutableList.builder();
    kidsPlaying.forEach(builder::add);
    return builder.build();
  }

  @Override
  public List<Kid> kidsWaiting() {
    ImmutableList.Builder<Kid> builder = ImmutableList.builder();
    kidsWaiting.forEach(builder::add);
    return builder.build();
  }

  public void setMaxKidsPlaying(int maxKidsPlaying) {
    this.maxKidsPlaying = maxKidsPlaying;
  }
}
