using System.Collections.Generic;
using TimeCapsule.Domain.Common;

namespace TimeCapsule.Domain.Entities
{
    public class User : AudiableEntity
    {
        public string Username { get; set; }

        public string Password { get; set; }

        public string Email { get; set; }

        public bool IsAdmin { get; set; } = false;

        public bool IsUser { get; set; }

        public virtual IList<TimeTaskClass> TaskClasses { get; set; }

        public virtual IList<TimeDaily> Dailies { get; set; }

        public virtual IList<TimeTask> Tasks { get; set; }
    }
}
