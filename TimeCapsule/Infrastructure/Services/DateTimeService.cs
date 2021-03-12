using System;
using System.Collections.Generic;
using System.Text;
using TimeCapsule.Application.Common.Interfaces;

namespace TimeCapsule.Infrastructure.Services
{
    class DateTimeService : IDateTimeService
    {
        public DateTime Now => DateTime.Now;
    }
}
