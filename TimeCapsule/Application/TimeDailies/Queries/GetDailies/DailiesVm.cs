using System;
using System.Collections.Generic;
using System.Text;

namespace TimeCapsule.Application.TimeDailies.Queries.GetDailies
{
    public class DailiesVm
    {
        public int Total { get; set; }

        public IList<DailyDto> Dailies { get; set; }
    }
}
