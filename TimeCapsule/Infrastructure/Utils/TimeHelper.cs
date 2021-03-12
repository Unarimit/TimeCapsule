using System;
using System.Collections.Generic;
using System.Text;

namespace TimeCapsule.Infrastructure.Utils
{
    public class TimeHelper
    {
        public static DateTime ConvertFromUnixTimestamp(long timestamp)
        {
            return new DateTime(1970, 1, 1, 0, 0, 0)
                .AddSeconds(timestamp);
        }

        public static long ConvertToUnixTimestamp(DateTime dateTime)
        {
            DateTime startTime = new DateTime(1970, 1, 1, 0, 0, 0);
            return (int)(dateTime - startTime).TotalSeconds;
        }
    }
}
