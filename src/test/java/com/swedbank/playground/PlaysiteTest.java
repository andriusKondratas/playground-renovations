package com.swedbank.playground;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class PlaysiteTest {

	@Mock
	private IVisitRepository visitRepository;

	@InjectMocks
	private Playsite playsite;

	@Before
	public void setup() {
		playsite.setPlaysiteType(PlaysiteType.SLIDE);
		playsite.setMaxKidsPlaying(1);
	}

	@Test
	public void testAddKid() {
		Kid kid = new Kid();
		kid.setName("John Smith");
		kid.setAge(5);

		boolean playing = playsite.addKid(kid);
		assertEquals(true, playing);
		assertEquals(ImmutableList.of(kid), playsite.kidsPlaying());

		final Visit visit = new Visit();
		visit.setPlaysite(playsite);
		visit.setKid(kid);
		visit.setStart(true);

		Mockito.verify(visitRepository).save(argThat(new ArgumentMatcher<Visit>() {
			@Override
			public boolean matches(Object argument) {
				Visit v = (Visit)argument;
				return visit.getPlaysite().equals(v.getPlaysite())
					&& visit.getKid().equals(v.getKid())
					&& visit.isStart() == v.isStart();
			}
		}));

		Kid kid2 = new Kid();
		kid.setName("Johnus Smithus");
		kid.setAge(6);

		playing = playsite.addKid(kid2);
		assertEquals(false, playing);
		assertEquals(ImmutableList.of(kid2), playsite.kidsWaiting());

		Mockito.verify(visitRepository, times(1)).save(any(Visit.class));
	}

	@Test
	public void testRemoveKid() {
		Kid kid = new Kid();
		kid.setName("John Smith");
		kid.setAge(5);

		boolean playing = playsite.addKid(kid);
		assertEquals(true, playing);
		assertEquals(ImmutableList.of(kid), playsite.kidsPlaying());

		playsite.removeKid(kid);
		assertEquals(ImmutableList.of(), playsite.kidsPlaying());

		final Visit visit = new Visit();
		visit.setPlaysite(playsite);
		visit.setKid(kid);
		visit.setStart(false);

		Mockito.verify(visitRepository).save(argThat(new ArgumentMatcher<Visit>() {
			@Override
			public boolean matches(Object argument) {
				Visit v = (Visit)argument;
				return visit.getPlaysite().equals(v.getPlaysite())
					&& visit.getKid().equals(v.getKid())
					&& visit.isStart() == v.isStart();
			}
		}));

		playsite.addKid(kid);


		Kid kid2 = new Kid();
		kid.setName("Johnus Smithus");
		kid.setAge(6);

		playing = playsite.addKid(kid2);

		playsite.removeKid(kid2);
		assertEquals(ImmutableList.of(), playsite.kidsWaiting());

		playsite.addKid(kid2);
		playsite.removeKid(kid);

		assertEquals(ImmutableList.of(kid2), playsite.kidsPlaying());
	}

}
