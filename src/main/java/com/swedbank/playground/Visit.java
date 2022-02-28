package com.swedbank.playground;

import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public final class Visit {

  PlaySite playSite;
  Kid kid;
  Date time;
  boolean isStart;  // true - start of visit; false - end of visit

}
