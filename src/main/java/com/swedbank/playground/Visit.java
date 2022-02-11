package com.swedbank.playground;

import com.google.common.base.MoreObjects;

import java.util.Date;
import java.util.Objects;

public final class Visit {

  private Playsite playsite;
  private Kid kid;
  private Date time;
  private boolean isStart;  // true - start of visit; false - end of visit

  public Kid getKid() {
    return kid;
  }

  public void setKid(Kid kid) {
    this.kid = kid;
  }

  public Date getTime() {
    return time;
  }

  public void setTime(Date time) {
    this.time = time;
  }

  public boolean isStart() {
    return isStart;
  }

  public void setStart(boolean start) {
    isStart = start;
  }

  public Playsite getPlaysite() {
    return playsite;
  }

  public void setPlaysite(Playsite playsite) {
    this.playsite = playsite;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
      .add("playsite", playsite)
      .add("kid", kid)
      .add("time", time)
      .add("isStart", isStart)
      .toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Visit visit = (Visit)o;
    return isStart() == visit.isStart() &&
      Objects.equals(getPlaysite(), visit.getPlaysite()) &&
      Objects.equals(getKid(), visit.getKid()) &&
      Objects.equals(getTime(), visit.getTime());
  }

}
