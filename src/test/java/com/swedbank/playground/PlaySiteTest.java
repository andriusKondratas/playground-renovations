package com.swedbank.playground;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.times;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PlaySiteTest {

  @Mock
  private IVisitRepository visitRepository;

  @InjectMocks
  private PlaySite playsite;

  @Before
  public void setup() {
    playsite.setMaxKidsPlaying(1);
  }

  @Test
  public void testAddKid() {
    var kid = new Kid();
    kid.setName("John Smith");
    kid.setAge(5);

    var playing = playsite.addKid(kid);
    assertTrue(playing);
    assertEquals(ImmutableList.of(kid), playsite.kidsPlaying());

    final var visit = new Visit();
    visit.setPlaySite(playsite);
    visit.setKid(kid);
    visit.setStart(true);

    Mockito.verify(visitRepository).save(argThat(new ArgumentMatcher<>() {
      @Override
      public boolean matches(Object argument) {
        var v = (Visit) argument;
        return visit.getPlaySite().equals(v.getPlaySite())
            && visit.getKid().equals(v.getKid())
            && visit.isStart() == v.isStart();
      }
    }));

    var kid2 = new Kid();
    kid.setName("Johnus Smithus");
    kid.setAge(6);

    playing = playsite.addKid(kid2);
    assertFalse(playing);
    assertEquals(ImmutableList.of(kid2), playsite.kidsWaiting());

    Mockito.verify(visitRepository, times(1)).save(any(Visit.class));
  }

  @Test
  public void testRemoveKid() {
    var kid = new Kid();
    kid.setName("John Smith");
    kid.setAge(5);

    var playing = playsite.addKid(kid);
    assertTrue(playing);
    assertEquals(ImmutableList.of(kid), playsite.kidsPlaying());

    playsite.removeKid(kid);
    assertEquals(ImmutableList.of(), playsite.kidsPlaying());

    final var visit = new Visit();
    visit.setPlaySite(playsite);
    visit.setKid(kid);
    visit.setStart(false);

    Mockito.verify(visitRepository).save(argThat(new ArgumentMatcher<>() {
      @Override
      public boolean matches(Object argument) {
        var v = (Visit) argument;
        return visit.getPlaySite().equals(v.getPlaySite())
            && visit.getKid().equals(v.getKid())
            && visit.isStart() == v.isStart();
      }
    }));

    playsite.addKid(kid);

    var kid2 = new Kid();
    kid.setName("Johnus Smithus");
    kid.setAge(6);

    playsite.addKid(kid2);

    playsite.removeKid(kid2);
    assertEquals(ImmutableList.of(), playsite.kidsWaiting());

    playsite.addKid(kid2);
    playsite.removeKid(kid);

    assertEquals(ImmutableList.of(kid2), playsite.kidsPlaying());
  }

}
