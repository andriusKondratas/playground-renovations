package com.swedbank.playground;

import java.util.List;

public interface IPlaysite {

  PlaysiteType getPlaysiteType();

  boolean addKid(Kid kid);

  void removeKid(Kid kid);

  List<Kid> kidsPlaying();

  List<Kid> kidsWaiting();

}
