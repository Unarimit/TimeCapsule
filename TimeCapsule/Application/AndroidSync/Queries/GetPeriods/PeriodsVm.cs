using System;
using System.Collections.Generic;
using System.Text;
using TimeCapsule.Application.AndroidSync.AndroidDtos;

namespace TimeCapsule.Application.AndroidSync.Queries.GetPeriods
{
    public class SyncPeriodsVm
    {
        public IList<ATimePeriod> ATimePeriods { get; set; }

        public bool IsFinish { get; set; }
    }
}
