package com.swedbank.playground;

import java.util.List;

public interface IPlaySite {

  boolean addKid(Kid kid);

  void removeKid(Kid kid);

  List<Kid> kidsPlaying();

  List<Kid> kidsWaiting();

}
