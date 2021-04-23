using System;
using System.Collections.Generic;
using System.Text;

namespace TimeCapsule.Application.Utils
{
    public class TimeHelper
    {
        /// <summary>
        /// input is seconds
        /// </summary>
        /// <param name="timestamp"></param>
        /// <returns></returns>
        public static DateTime ConvertFromUnixTimestamp(long timestamp)
        {
            return new DateTime(1970, 1, 1, 0, 0, 0)
                .AddSeconds(timestamp);
        }
        /// <summary>
        /// output is seconds
        /// </summary>
        /// <param name="timestamp"></param>
        /// <returns></returns>
        public static long ConvertToUnixTimestamp(DateTime dateTime)
        {
            DateTime startTime = new DateTime(1970, 1, 1, 0, 0, 0);
            return (int)(dateTime - startTime).TotalSeconds;
        }
    }
}
